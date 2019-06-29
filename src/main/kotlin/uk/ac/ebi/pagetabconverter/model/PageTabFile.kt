package uk.ac.ebi.pagetabconverter.model

data class PageTabFile(val path: String, val attributes: List<Attribute>)

data class Attribute(val name: String, val value: String)
