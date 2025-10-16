package com.my.data.mappers

import com.my.data.database.LocalCharacter
import com.my.data.models.CharacterNetwork
import com.my.data.models.CharacterResponse
import com.my.domain.models.Character

class Mapper {

    fun mapCharacterResponseToCharacter(characterNetwork: CharacterNetwork): Character {
        return Character(
            id = characterNetwork.id,
            name = characterNetwork.name,
            status = characterNetwork.status,
            species = characterNetwork.species,
            type = characterNetwork.type,
            gender = characterNetwork.gender,
            image = characterNetwork.image,
            origin = characterNetwork.origin.name,
            location = characterNetwork.location.name
        )
    }

    fun mapCharacterResponseToLocalCharacter(characterNetwork: CharacterNetwork, page: Int): LocalCharacter {
        return LocalCharacter(
            id = characterNetwork.id,
            name = characterNetwork.name,
            status = characterNetwork.status,
            species = characterNetwork.species,
            type = characterNetwork.type,
            gender = characterNetwork.gender,
            image = characterNetwork.image,
            origin = characterNetwork.origin.name,
            location = characterNetwork.location.name,
            page = page
        )
    }

    fun mapLocalCharacterToCharacter(localCharacter: LocalCharacter): Character {
        return Character(
            id = localCharacter.id,
            name = localCharacter.name,
            status = localCharacter.status,
            species = localCharacter.species,
            type = localCharacter.type,
            gender = localCharacter.gender,
            image = localCharacter.image,
            origin = localCharacter.origin,
            location = localCharacter.location
        )
    }
}