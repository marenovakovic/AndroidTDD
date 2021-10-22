package com.example.androidtdd.users.api.mappers

import com.example.androidtdd.users.api.models.AddressDto
import com.example.androidtdd.users.api.models.CompanyDto
import com.example.androidtdd.users.api.models.UserDto
import com.example.androidtdd.users.models.Address
import com.example.androidtdd.users.models.Company
import com.example.androidtdd.users.models.Location
import com.example.androidtdd.users.models.User

fun UserDto.toUser(): User =
    User(
        id = id,
        name = name,
        email = email,
        userName = username,
        address = address.toAddress(),
        phone = phone,
        website = website,
        company = company.toCompany(),
    )

fun AddressDto.toAddress(): Address =
    Address(
        location = Location(geo.lat, geo.lng),
        zipCode = zipcode,
        city = city,
        street = street,
        suite = suite,
    )

fun CompanyDto.toCompany(): Company =
    Company(
        name = name,
        catchPhrase = catchPhrase,
        bs = bs,
    )