package com.example.appheladeria.screens

import android.Manifest
import android.content.ClipData
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Camera
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.core.content.FileProvider
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.appheladeria.ui.theme.PrimaryPink
import com.example.appheladeria.ui.theme.TextDark
import java.io.File
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun PhotoBoothScreen(
    onBack: () -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    var hasPermission by remember {
        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    var lastPhotoUri by remember {
        mutableStateOf<Uri?>(null)
    }

    var isSaving by remember {
        mutableStateOf(false)
    }

    val permissionLauncher =
        rememberLauncherForActivityResult(
            ActivityResultContracts.RequestPermission()
        ) { granted ->
            hasPermission = granted
        }

    LaunchedEffect(Unit) {
        if (!hasPermission) {
            permissionLauncher.launch(Manifest.permission.CAMERA)
        }
    }

    val imageCapture = remember {
        ImageCapture.Builder().build()
    }

    fun takePhoto() {
        if (isSaving) return

        isSaving = true

        val photoName =
            "ScoopSmile_" +
                    SimpleDateFormat(
                        "yyyyMMdd_HHmmss",
                        Locale.US
                    ).format(System.currentTimeMillis()) +
                    ".jpg"

        val contentValues = ContentValues().apply {
            put(
                MediaStore.MediaColumns.DISPLAY_NAME,
                photoName
            )

            put(
                MediaStore.MediaColumns.MIME_TYPE,
                "image/jpeg"
            )

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                put(
                    MediaStore.MediaColumns.RELATIVE_PATH,
                    "Pictures/ScoopSmile"
                )
            }
        }

        val outputOptions =
            ImageCapture.OutputFileOptions.Builder(
                context.contentResolver,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            ).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(context),
            object : ImageCapture.OnImageSavedCallback {

                override fun onImageSaved(
                    outputFileResults: ImageCapture.OutputFileResults
                ) {
                    isSaving = false

                    val savedUri = outputFileResults.savedUri

                    if (savedUri != null) {
                        lastPhotoUri = savedUri

                        Toast.makeText(
                            context,
                            "Foto guardada en galería",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            context,
                            "La foto se guardó, pero no se pudo preparar para compartir",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

                override fun onError(
                    exception: ImageCaptureException
                ) {
                    isSaving = false

                    Toast.makeText(
                        context,
                        "No se pudo guardar la foto",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        )
    }

    fun shareLastPhoto() {
        val originalUri = lastPhotoUri

        if (originalUri == null) {
            Toast.makeText(
                context,
                "Primero toma una foto",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        try {
            val inputStream =
                context.contentResolver.openInputStream(originalUri)

            if (inputStream == null) {
                Toast.makeText(
                    context,
                    "No se pudo leer la foto",
                    Toast.LENGTH_SHORT
                ).show()

                return
            }

            val shareDir =
                File(
                    context.cacheDir,
                    "shared_images"
                )

            if (!shareDir.exists()) {
                shareDir.mkdirs()
            }

            val shareFile =
                File(
                    shareDir,
                    "scoop_smile_share.jpg"
                )

            inputStream.use { input ->
                shareFile.outputStream().use { output ->
                    input.copyTo(output)
                }
            }

            val shareUri =
                FileProvider.getUriForFile(
                    context,
                    "${context.packageName}.provider",
                    shareFile
                )

            val shareIntent =
                Intent(Intent.ACTION_SEND).apply {
                    type = "image/jpeg"

                    putExtra(
                        Intent.EXTRA_STREAM,
                        shareUri
                    )

                    putExtra(
                        Intent.EXTRA_TEXT,
                        "Mi momento dulce en Scoop & Smile 🍦"
                    )

                    clipData =
                        ClipData.newUri(
                            context.contentResolver,
                            "Foto Scoop & Smile",
                            shareUri
                        )

                    addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
                }

            val chooser =
                Intent.createChooser(
                    shareIntent,
                    "Compartir foto"
                )

            chooser.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

            context.startActivity(chooser)

        } catch (e: Exception) {
            Toast.makeText(
                context,
                "No se pudo compartir la foto",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFFFF7FC))
            .navigationBarsPadding()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            if (hasPermission) {
                AndroidView(
                    factory = { ctx ->

                        val previewView = PreviewView(ctx)

                        val cameraProviderFuture =
                            ProcessCameraProvider.getInstance(ctx)

                        cameraProviderFuture.addListener({

                            val cameraProvider =
                                cameraProviderFuture.get()

                            val preview =
                                Preview.Builder().build()

                            preview.setSurfaceProvider(
                                previewView.surfaceProvider
                            )

                            val cameraSelector =
                                CameraSelector.DEFAULT_FRONT_CAMERA

                            try {
                                cameraProvider.unbindAll()

                                cameraProvider.bindToLifecycle(
                                    lifecycleOwner,
                                    cameraSelector,
                                    preview,
                                    imageCapture
                                )

                            } catch (_: Exception) {
                            }

                        }, ContextCompat.getMainExecutor(ctx))

                        previewView
                    },
                    modifier = Modifier.fillMaxSize()
                )

            } else {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Permiso de cámara requerido",
                        color = Color.White
                    )
                }
            }

            IconButton(
                onClick = onBack,
                modifier = Modifier
                    .padding(20.dp)
                    .background(
                        Color.White.copy(alpha = 0.85f),
                        CircleShape
                    )
                    .align(Alignment.TopStart)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = "Volver",
                    tint = TextDark
                )
            }

            Card(
                modifier = Modifier
                    .padding(top = 22.dp)
                    .align(Alignment.TopCenter),
                shape = RoundedCornerShape(24.dp),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White.copy(alpha = 0.86f)
                )
            ) {
                Row(
                    modifier = Modifier.padding(
                        horizontal = 18.dp,
                        vertical = 10.dp
                    ),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "🍦",
                        style = MaterialTheme.typography.titleLarge
                    )

                    Spacer(modifier = Modifier.size(8.dp))

                    Column {
                        Text(
                            text = "Scoop & Smile Spot",
                            fontWeight = FontWeight.ExtraBold,
                            color = TextDark
                        )

                        Text(
                            text = "Tómate una foto con tu helado",
                            color = TextDark,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 32.dp)
                    .aspectRatio(0.76f)
                    .align(Alignment.Center)
                    .border(
                        width = 5.dp,
                        color = PrimaryPink,
                        shape = RoundedCornerShape(38.dp)
                    )
            ) {
                Text(
                    text = "✨ Mi momento dulce ✨",
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 18.dp)
                        .background(
                            Color.White.copy(alpha = 0.75f),
                            RoundedCornerShape(18.dp)
                        )
                        .padding(
                            horizontal = 14.dp,
                            vertical = 6.dp
                        ),
                    color = PrimaryPink,
                    fontWeight = FontWeight.ExtraBold
                )

                Text(
                    text = "Scoop & Smile 🍨",
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 18.dp)
                        .background(
                            Color.White.copy(alpha = 0.78f),
                            RoundedCornerShape(18.dp)
                        )
                        .padding(
                            horizontal = 16.dp,
                            vertical = 7.dp
                        ),
                    color = TextDark,
                    fontWeight = FontWeight.Bold
                )
            }
        }

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            shape = RoundedCornerShape(30.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color.White
            )
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Foto del cliente",
                    color = TextDark,
                    fontWeight = FontWeight.ExtraBold,
                    style = MaterialTheme.typography.titleLarge
                )

                Spacer(modifier = Modifier.height(6.dp))

                Text(
                    text = if (lastPhotoUri == null) {
                        "Toma una foto, guárdala y compártela."
                    } else {
                        "Foto lista para compartir."
                    },
                    color = Color(0xFF6F6670)
                )

                Spacer(modifier = Modifier.height(18.dp))

                Button(
                    onClick = {
                        takePhoto()
                    },
                    enabled = !isSaving,
                    shape = CircleShape,
                    colors = ButtonDefaults.buttonColors(
                        containerColor = PrimaryPink
                    ),
                    modifier = Modifier.size(82.dp),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Camera,
                        contentDescription = "Tomar foto",
                        tint = Color.White,
                        modifier = Modifier.size(34.dp)
                    )
                }

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = if (isSaving) {
                        "Guardando foto..."
                    } else {
                        "Tocar para tomar y guardar"
                    },
                    color = TextDark,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(18.dp))

                OutlinedButton(
                    onClick = {
                        shareLastPhoto()
                    },
                    enabled = lastPhotoUri != null,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Share,
                        contentDescription = "Compartir",
                        tint = if (lastPhotoUri != null) {
                            PrimaryPink
                        } else {
                            Color.Gray
                        }
                    )

                    Spacer(modifier = Modifier.size(8.dp))

                    Text(
                        text = "Compartir última foto",
                        color = if (lastPhotoUri != null) {
                            PrimaryPink
                        } else {
                            Color.Gray
                        },
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}