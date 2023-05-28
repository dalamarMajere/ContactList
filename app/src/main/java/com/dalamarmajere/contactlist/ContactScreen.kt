package com.dalamarmajere.contactlist

import android.provider.MediaStore.Audio.Radio
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ContactScreen(
    state: ContactState,
    onEvent: (ContactEvent) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { onEvent(ContactEvent.ShowDialog) }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add contact")
            }
        }
    ) { padding ->
        
        if (state.isAddingContact) {
            AddContactDialog(state, onEvent)
        }
        
        
        LazyColumn(modifier = Modifier
            .padding(padding)
            .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            content = {
                item {
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .horizontalScroll(rememberScrollState()),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        SortType.values().forEach { sortType ->
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.clickable { onEvent(ContactEvent.SortContacts(sortType)) }
                            ) {
                                RadioButton(
                                    selected = state.sortType == sortType,
                                    onClick = { onEvent(ContactEvent.SortContacts(sortType)) }
                                )
                                Text(text = sortType.name)
                            }
                        }
                    }
                }
                items(state.contacts) { contact ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(10.dp)
                    ) {
                        Column(
                            modifier = Modifier.weight(1f)
                        ) {
                            Text(
                                text = contact.firstName + " " + contact.secondName,
                                fontSize = 20.sp
                            )
                            Text(text = contact.phoneNumber)
                        }
                        IconButton(onClick = {
                            onEvent(ContactEvent.DeleteContact(contact))
                        }) {
                            Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete contact")
                        }
                    }
                }
        })
    }
}

@Composable
fun AddContactDialog(
    state: ContactState,
    onEvent: (ContactEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    AlertDialog(
        modifier = Modifier,
        onDismissRequest = { onEvent(ContactEvent.HideDialog) },
        title = { Text(text = "Add contact") },
        text = {
            Column(
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    value = state.firstname,
                    onValueChange = {
                        onEvent(ContactEvent.SetFirstName(it))
                    },
                    placeholder = {
                        Text(text = "Enter first name")
                    }
                )
                TextField(
                    value = state.secondName,
                    onValueChange = {
                        onEvent(ContactEvent.SetSecondName(it))
                    },
                    placeholder = {
                        Text(text = "Enter second name")
                    }
                )
                TextField(
                    value = state.phoneNumber,
                    onValueChange = {
                        onEvent(ContactEvent.SetPhoneNumber(it))
                    },
                    placeholder = {
                        Text(text = "Enter phone number")
                    }
                )
            }
        },
        buttons = {
            Box(modifier = Modifier
                .fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Button(onClick = { onEvent(ContactEvent.SaveContact) }) {
                    Text(text = "Save")
                }
            }
        }
    )
}