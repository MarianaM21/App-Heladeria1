package com.example.appheladeria.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
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
import androidx.lifecycle.compose.LocalLifecycleOwner
import com.example.appheladeria.ui.theme.BackgroundSoft
import com.example.appheladeria.ui.theme.PrimaryPink
import com.example.appheladeria.ui.theme.TextDark
import com.example.appheladeria.ui.theme.TextMuted
import com.example.appheladeria.utils.QrCodeAnalyzer
import java.util.concurrent.Executors

@Composable
fun QrScannerScreen(
    onBack: () -> Unit,
    onPromoDetected: () -> Unit
) {

    val context = LocalContext.current

    val lifecycleOwner =
        LocalLifecycleOwner.current

    var hasPermission by remember {

        mutableStateOf(
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        )
    }

    var scannedText by remember {

        mutableStateOf("")
    }

    var alreadyScanned by remember {

        mutableStateOf(false)
    }

    val permissionLauncher =
        rememberLauncherForActivityResult(
            contract =
                ActivityResultContracts
                    .RequestPermission()
        ) {

            hasPermission = it
        }

    LaunchedEffect(Unit) {

        if (!hasPermission) {

            permissionLauncher.launch(
                Manifest.permission.CAMERA
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundSoft)
            .padding(16.dp)
    ) {

        IconButton(
            onClick = onBack
        ) {

            Icon(
                imageVector =
                    Icons.Default.ArrowBack,

                contentDescription = null,

                tint = TextDark
            )
        }

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        Text(
            text = "Escanea el QR",

            style =
                MaterialTheme.typography
                    .headlineMedium,

            fontWeight = FontWeight.Bold,

            color = TextDark
        )

        Spacer(
            modifier = Modifier.height(8.dp)
        )

        Text(
            text =
                "Escanea promociones 🍦",

            color = TextMuted
        )

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f),

            shape =
                RoundedCornerShape(30.dp),

            colors =
                CardDefaults.cardColors(
                    containerColor =
                        Color.Black
                )
        ) {

            if (hasPermission) {

                AndroidView(

                    factory = { ctx ->

                        val previewView =
                            PreviewView(ctx)

                        val cameraProviderFuture =
                            ProcessCameraProvider
                                .getInstance(ctx)

                        cameraProviderFuture
                            .addListener({

                                val cameraProvider =
                                    cameraProviderFuture
                                        .get()

                                val preview =
                                    Preview.Builder()
                                        .build()

                                preview.setSurfaceProvider(
                                    previewView.surfaceProvider
                                )

                                val imageAnalysis =
                                    ImageAnalysis.Builder()

                                        .setBackpressureStrategy(
                                            ImageAnalysis
                                                .STRATEGY_KEEP_ONLY_LATEST
                                        )

                                        .build()

                                imageAnalysis.setAnalyzer(
                                    Executors
                                        .newSingleThreadExecutor(),

                                    QrCodeAnalyzer { result ->

                                        ContextCompat
                                            .getMainExecutor(ctx)
                                            .execute {

                                                if (!alreadyScanned) {

                                                    alreadyScanned = true

                                                    scannedText = result

                                                    if (
                                                        result.contains(
                                                            "promo",
                                                            ignoreCase = true
                                                        ) ||
                                                        result.contains(
                                                            "qrco.de/bgpcLT",
                                                            ignoreCase = true
                                                        )
                                                    ) {

                                                        onPromoDetected()
                                                    }
                                                }
                                            }
                                    }
                                )

                                val cameraSelector =
                                    CameraSelector
                                        .DEFAULT_BACK_CAMERA

                                try {

                                    cameraProvider
                                        .unbindAll()

                                    cameraProvider
                                        .bindToLifecycle(
                                            lifecycleOwner,
                                            cameraSelector,
                                            preview,
                                            imageAnalysis
                                        )

                                } catch (_: Exception) {
                                }

                            },
                                ContextCompat
                                    .getMainExecutor(ctx)
                            )

                        previewView
                    },

                    modifier =
                        Modifier.fillMaxSize()
                )

            } else {

                Box(
                    modifier = Modifier.fillMaxSize(),

                    contentAlignment =
                        Alignment.Center
                ) {

                    Text(
                        text =
                            "Permiso de cámara requerido",

                        color = Color.White
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.height(20.dp)
        )

        if (scannedText.isNotEmpty()) {

            Card(
                modifier =
                    Modifier.fillMaxWidth(),

                shape =
                    RoundedCornerShape(20.dp)
            ) {

                Column(
                    modifier =
                        Modifier.padding(16.dp)
                ) {

                    Text(
                        text = "QR Detectado",

                        fontWeight =
                            FontWeight.Bold
                    )

                    Spacer(
                        modifier =
                            Modifier.height(8.dp)
                    )

                    Text(
                        text = scannedText,

                        color = PrimaryPink
                    )
                }
            }
        }
    }
}