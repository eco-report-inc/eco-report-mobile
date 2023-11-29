package com.capstone.ecoreport.ui.theme.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.AddAPhoto
import androidx.compose.material.icons.outlined.Map
import androidx.compose.material.icons.outlined.Send
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.capstone.ecoreport.ui.theme.EcoReportTheme

@Composable
fun CreateForm() {
    var description by remember { mutableStateOf("") }
    var locationName by remember { mutableStateOf("") }
    var longitude by remember { mutableStateOf("") }
    var latitude by remember { mutableStateOf("") }
    var photoUri by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Text Field for Location Name (Use the default height) with Placeholder
        LabeledTextField(
            label = "Lokasi",
            value = locationName
        ) {
            locationName = it
        }
        // Text Field for Description (Set the height to your preference)
        LabeledTextField(
            label = "Deskripsi",
            value = description,
            height = 120.dp,
            placeholder = "Tuliskan deskripsi disini",
        ) {
            description = it
        }


        // Row for Buttons (Get Location and Take Photo)
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Button to Access Maps and Get GPS Location
            LabeledButton(
                onClick = { /* Handle Maps Integration Here */ },
                label = "Ambil Lokasi",
                modifier = Modifier.weight(1f)
            )
        }

        // Button to Submit Form
        Button(
            onClick = {
                // Handle Form Submission Here
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(imageVector = Icons.Outlined.Send, contentDescription = null)
                Text("Submit")
            }
        }
    }
}

@Composable
fun LabeledTextField(
    label: String,
    value: String,
    height: Dp = 40.dp,
    placeholder: String? = null,
    onValueChange: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(text = label)
        TextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { placeholder?.let { Text(it) } },
            modifier = Modifier
                .fillMaxWidth()
                .height(height)
        )
    }
}

@Composable
fun LabeledButton(onClick: () -> Unit, label: String, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
    ) {
        Button(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 40.dp)
        ) {
            Text(label)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CreateFormPreview() {
    EcoReportTheme {
        CreateForm()
    }
}