package com.example.androidtdd

import com.example.androidtdd.users.api.fake.FakeUsersApi
import com.example.androidtdd.users.api.mappers.toUser
import com.example.androidtdd.users.api.models.AddressDto
import com.example.androidtdd.users.api.models.CompanyDto
import com.example.androidtdd.users.api.models.GeoDto
import com.example.androidtdd.users.api.models.UserDto
import com.example.androidtdd.users.models.Address
import com.example.androidtdd.users.models.Company
import com.example.androidtdd.users.models.Location
import com.example.androidtdd.users.models.User
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class UserDtoMapperTest {

    @Test
    fun `should map UserDto fields to corresponding User fields`() = runBlocking {
        val dto = getUserDto()
        val user = dto.toUser()

        checkFields(user, dto)
    }

    private fun checkFields(
        user: User,
        userDto: UserDto
    ) {
        checkUserFields(user, userDto)
        checkAddressFields(user.address, userDto.address)
        checkCompanyFields(user.company, userDto.company)
    }

    private fun checkUserFields(
        user: User,
        dto: UserDto
    ) {
        assertEquals(user.id, dto.id)
        assertEquals(user.name, dto.name)
        assertEquals(user.email, dto.email)
        assertEquals(user.userName, dto.username)
        assertEquals(user.phone, dto.phone)
        assertEquals(user.website, dto.website)
    }

    private fun checkAddressFields(address: Address, addressDto: AddressDto) {
        checkLocationFields(address.location, addressDto.geo)

        assertEquals(address.zipCode, addressDto.zipcode)
        assertEquals(address.city, addressDto.city)
        assertEquals(address.street, addressDto.street)
        assertEquals(address.suite, addressDto.suite)
    }

    private fun checkLocationFields(location: Location, geo: GeoDto) {
        assertEquals(location.latitude, geo.lat)
        assertEquals(location.longitude, geo.lng)
    }

    private fun checkCompanyFields(company: Company, companyDto: CompanyDto) {
        assertEquals(company.name, companyDto.name)
        assertEquals(company.catchPhrase, companyDto.catchPhrase)
        assertEquals(company.bs, companyDto.bs)
    }

    private suspend fun getUserDto() = FakeUsersApi.fetchUsers().first()
}