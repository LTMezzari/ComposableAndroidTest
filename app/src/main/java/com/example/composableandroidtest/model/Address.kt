package com.example.composableandroidtest.model

import com.google.gson.annotations.SerializedName

open class Address (
    @SerializedName("cep")
    val cep: String,
    @SerializedName("logradouro")
    val street: String,
    @SerializedName("complemento")
    val complement: String,
    @SerializedName("bairro")
    val neighborhood: String,
    @SerializedName("localidade")
    val locality: String,
    @SerializedName("uf")
    val state: String,
    @SerializedName("unidade")
    val unity: String,
    @SerializedName("ibge")
    val ibge: String,
    @SerializedName("gia")
    val gia: String
)