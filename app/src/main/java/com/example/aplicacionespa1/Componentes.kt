package com.example.aplicacionespa1.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.example.aplicacionespa1.ui.theme.*

/*inputs*/
@Composable
fun AuthTextField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    placeholder: String,
    modifier: Modifier = Modifier,
    isPassword: Boolean = false,
    keyboardType: KeyboardType = KeyboardType.Text,
    error: String? = null,
) {
    var visible by remember { mutableStateOf(false) }

    val trailing: (@Composable () -> Unit)? = if (isPassword) {
        {
            IconButton(onClick = { visible = !visible }) {
                Icon(
                    imageVector = if (visible) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                    contentDescription = if (visible) "Ocultar contraseña" else "Mostrar contraseña",
                    tint = TextGray,
                )
            }
        }
    } else null

    Column(modifier = modifier.fillMaxWidth()) {
        Text(label, style = MaterialTheme.typography.titleMedium, color = TextDark)
        Spacer(modifier = Modifier.height(8.dp))
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = { Text(placeholder, color = TextGray) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(10.dp),
            isError = error != null,
            supportingText = error?.let { { Text(it) } },
            visualTransformation = if (isPassword && !visible) PasswordVisualTransformation() else VisualTransformation.None,
            keyboardOptions = KeyboardOptions(keyboardType = if (isPassword) KeyboardType.Password else keyboardType),
            trailingIcon = trailing,
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = PrimaryBrown,
                unfocusedBorderColor = Color(0xFFBDBDBD),
                cursorColor = PrimaryBrown,
            ),
        )
    }
}

/*cards*/
@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    onClick: (() -> Unit)? = null,
    containerColor: Color = Color.White,
    content: @Composable ColumnScope.() -> Unit,
) {
    Card(
        modifier = if (onClick != null) modifier.clickable(onClick = onClick) else modifier,
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
        border = BorderStroke(1.dp, Color(0xFFEDE0D4)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        content = content,
    )
}

@Composable
fun SocialCard(
    @DrawableRes icon: Int,
    contentDescription: String,
    onClick: () -> Unit,
) {
    AppCard(onClick = onClick) {
        Image(
            painter = painterResource(icon),
            contentDescription = contentDescription,
            modifier = Modifier.padding(12.dp).size(28.dp),
        )
    }
}

@Composable
fun SocialLoginRow(
    @DrawableRes googleIcon: Int,
    @DrawableRes facebookIcon: Int,
    onGoogle: () -> Unit,
    onFacebook: () -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterHorizontally),
    ) {
        SocialCard(googleIcon, "Google", onGoogle)
        SocialCard(facebookIcon, "Facebook", onFacebook)
    }
}

/* modal */
@Composable
fun AppModal(
    title: String,
    onDismiss: () -> Unit,
    subtitle: String? = null,
    content: @Composable ColumnScope.() -> Unit,
) {
    Dialog(
        onDismissRequest = onDismiss,
        properties = DialogProperties(usePlatformDefaultWidth = false),
    ) {
        Surface(
            modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp),
            shape = RoundedCornerShape(24.dp),
            color = Color.White,
        ) {
            Column(modifier = Modifier.padding(24.dp)) {
                Box(modifier = Modifier.fillMaxWidth()) {
                    IconButton(onClick = onDismiss, modifier = Modifier.align(Alignment.CenterStart).offset(x = (-12).dp)) {
                        Icon(Icons.Filled.Close, contentDescription = "Cerrar", tint = PrimaryBrown)
                    }
                }
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = PrimaryBrown,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth(),
                )
                if (subtitle != null) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = subtitle,
                        fontSize = 12.sp,
                        color = TextGray,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.fillMaxWidth(),
                    )
                }
                Spacer(modifier = Modifier.height(24.dp))
                content()
            }
        }
    }
}

/* cosas comunes*/
@Composable
fun AuthScreen(
    title: String,
    onBack: () -> Unit,
    content: @Composable ColumnScope.() -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding()
            .imePadding()
            .padding(horizontal = 24.dp, vertical = 16.dp),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = onBack, modifier = Modifier.offset(x = (-12).dp)) {
                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver", tint = PrimaryBrown)
            }
            Text(title, style = MaterialTheme.typography.headlineSmall, color = PrimaryBrown)
        }
        Spacer(modifier = Modifier.height(24.dp))
        content()
    }
}

@Composable
fun AuthButton(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth().height(48.dp),
        shape = RoundedCornerShape(12.dp),
        colors = ButtonDefaults.buttonColors(containerColor = PrimaryBrown),
    ) {
        Text(text, color = Color.White, style = MaterialTheme.typography.labelLarge)
    }
}

@Composable
fun DividerWithText(text: String) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        HorizontalDivider(modifier = Modifier.weight(1f), color = TextGray)
        Text(text, modifier = Modifier.padding(horizontal = 12.dp), fontSize = 13.sp, color = TextDark)
        HorizontalDivider(modifier = Modifier.weight(1f), color = TextGray)
    }
}

@Composable
fun LinkText(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Text(
        text = text,
        modifier = modifier.clickable(onClick = onClick),
        color = PrimaryBrown,
        fontSize = 13.sp,
        fontWeight = FontWeight.Medium,
        textDecoration = TextDecoration.Underline,
    )
}

@Composable
fun AuthFooterLink(question: String, linkText: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text("$question ", fontSize = 13.sp, color = TextGray)
        LinkText(linkText, onClick)
    }
}