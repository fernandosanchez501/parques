package com.mgh.pmdm.parques.model

import java.io.Serializable

// Definimos la data class Park que modelará un parque
data class Park(
    var name: String,
    var desc: String?,
    var phone: String?,
    var webiste: String?,
    var openingTime: String?,
    var closingTime: String?,
    var sports: Boolean?,
    var children: Boolean?,
    var Bar: Boolean?,
    var Pets: Boolean?
): Serializable