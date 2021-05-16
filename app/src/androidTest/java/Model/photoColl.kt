package Model

import java.util.*

data class photoColl(
    val id: Int,
    val name: String,
    val dateCreate: Date,
    val description: String,
    val new: Boolean,
    val popular: Boolean,
    val image: image,
    val user: String
)
