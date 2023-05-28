package com.dalamarmajere.contactlist

data class ContactState(
    val contacts: List<Contact> = emptyList(),
    val firstname: String = "",
    val secondName: String = "",
    val phoneNumber: String = "",
    val isAddingContact: Boolean = false,
    val sortType: SortType = SortType.FIRST_NAME
)
