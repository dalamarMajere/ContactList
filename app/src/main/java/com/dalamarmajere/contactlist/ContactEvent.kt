package com.dalamarmajere.contactlist

sealed interface ContactEvent {
    object SaveContact: ContactEvent
    data class DeleteContact(val contact: Contact): ContactEvent
    data class SetFirstName(val firstName: String) : ContactEvent
    data class SetSecondName(val secondName: String) : ContactEvent
    data class SetPhoneNumber(val phoneNumber: String) : ContactEvent
    object ShowDialog: ContactEvent
    object HideDialog: ContactEvent
    data class SortContacts(val sortType : SortType): ContactEvent
}