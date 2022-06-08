package com.example.preair.camera

import android.Manifest
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.preair.databinding.ActivityCheckPictureBinding
import result.ResultActivity
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

class CheckPictureActivity : AppCompatActivity() {

    private lateinit var checkBinding: ActivityCheckPictureBinding
    private var getFile: File? = null

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        checkBinding = ActivityCheckPictureBinding.inflate(layoutInflater)
        setContentView(checkBinding.root)

        playAnimation()
        setPermissionCamera()
        allButtonAction()

    }

    private fun playAnimation() {
        val previewText = ObjectAnimator.ofFloat(checkBinding.pvText, View.ALPHA, 1f).setDuration(500)
        val previewImg = ObjectAnimator.ofFloat(checkBinding.previewImageView, View.ALPHA, 1f). setDuration(500)
        val askAction = ObjectAnimator.ofFloat(checkBinding.textView3, View.TRANSLATION_Y, 50f, 30f).setDuration(500)
        val imgGallery = ObjectAnimator.ofFloat(checkBinding.imgTakeGallery, View.TRANSLATION_Y, 1f).setDuration(500)
        val textGallery = ObjectAnimator.ofFloat(checkBinding.textView, View.TRANSLATION_Y, 1f).setDuration(500)
        val imgCamera = ObjectAnimator.ofFloat(checkBinding.imgTakeCamera, View.TRANSLATION_Y,1f).setDuration(500)
        val textCamera = ObjectAnimator.ofFloat(checkBinding.textView4, View.TRANSLATION_Y, 1f).setDuration(500)
        val imgResult = ObjectAnimator.ofFloat(checkBinding.imgSeeResult, View.TRANSLATION_Y, 1f).setDuration(500)
        val textResult = ObjectAnimator.ofFloat(checkBinding.textView2, View.TRANSLATION_Y, 1f).setDuration(500)

        val startAnim = AnimatorSet().apply {
            play(imgGallery).with(textGallery)
            play(imgCamera).with(textCamera)
            play(imgResult).with(textResult)
        }

        AnimatorSet().apply {
            playSequentially(previewText, previewImg, askAction, startAnim)
        }.start()
    }

    private fun setPermissionCamera() {
        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }
    }

    private fun allButtonAction() {
        checkBinding.apply {
            imgTakeGallery.setOnClickListener { startGallery() }
            imgTakeCamera.setOnClickListener { startCameraX() }
            imgSeeResult.setOnClickListener {
                startActivity(Intent(this@CheckPictureActivity, ResultActivity::class.java))
            }
        }
    }

    private fun startCameraX() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            getFile = myFile
            val result = rotateBitmap(
                BitmapFactory.decodeFile(getFile?.path),
                isBackCamera
            )

            checkBinding.previewImageView.setImageBitmap(result)
        }
    }


    private fun reduceFileImage(file: File): File {
        val bitmap = BitmapFactory.decodeFile(file.path)
        var compressQuality = 100
        var streamLength: Int
        do {
            val bmpStream = ByteArrayOutputStream()
            bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, bmpStream)
            val bmPicByArray = bmpStream.toByteArray()
            streamLength = bmPicByArray.size
            compressQuality -= 5
        } while (streamLength > 1000000)
        bitmap.compress(Bitmap.CompressFormat.JPEG, compressQuality, FileOutputStream(file))
        return file
    }

    private fun showLoading(isLoading: Boolean) {
        checkBinding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    private fun startGallery() {
        val intent = Intent()
        intent.action = Intent.ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImg: Uri = result.data?.data as Uri

            val myFile = uriToFile(selectedImg, this@CheckPictureActivity)

            getFile = myFile

            checkBinding.previewImageView.setImageURI(selectedImg)
        }
    }

    companion object {
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }
}