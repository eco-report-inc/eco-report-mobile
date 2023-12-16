package com.capstone.ecoreport.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.Wallpapers
import androidx.compose.ui.unit.dp
import com.capstone.ecoreport.R
import com.capstone.ecoreport.ui.theme.EcoReportTheme
import com.capstone.ecoreport.ui.components.EmailField
import com.capstone.ecoreport.ui.components.PasswordField
import com.capstone.ecoreport.ui.components.RepeatPasswordField

@Composable
fun EditProfileScreen() {

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var repeatPassword by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {

        Icon(
            imageVector = Icons.Default.ArrowBack,
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.Start)
                .clickable { /* Handle navigation back here */ }
                .padding(top = 8.dp, bottom = 16.dp)
        )

        // Headline Text
        Text(
            text = "Edit Profil",
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier
                .padding(bottom = 64.dp, top = 16.dp)
                .align(Alignment.Start)
        )

        // Circular profile picture
        Box(Modifier.height(143.dp)) {
            Box(
                modifier = Modifier
                    .padding(top = 8.dp)
                    .size(130.dp)
                    .clip(CircleShape)
                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_person),
                    contentDescription = null,
                    modifier = Modifier
                        .size(110.dp)
                        .clip(CircleShape)
                )
            }
            IconButton(
                onClick = { },
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .align(Alignment.BottomEnd),
                colors = IconButtonDefaults.iconButtonColors(MaterialTheme.colorScheme.primary)
            ) {
                Icon(
                    imageVector = Icons.Default.CameraAlt,
                    contentDescription = null,
                    modifier = Modifier
                        .size(24.dp),
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }

        Spacer(modifier = Modifier.height(32.dp))

        // TextFields for Name, Address, Email, Password, and Repeat Password
        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nama") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
        )

        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Alamat") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
        )

        EmailField(
            value = email,
            onValueChange = { email = it },
            label = "Email",
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        PasswordField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        RepeatPasswordField(
            value = repeatPassword,
            onValueChange = { repeatPassword = it },
            label = { Text("Repeat Password") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
    }
}
@Preview(
    showBackground = true,
    wallpaper = Wallpapers.GREEN_DOMINATED_EXAMPLE
)
@Composable
fun EditProfileScreenPreview() {
    EcoReportTheme {
        EditProfileScreen()
    }
}