package com.example.guidetovladivostok.mapper

import com.example.guidetovladivostok.dto.MarkerDto
import com.example.guidetovladivostok.entity.MarkerEntity

class MarkerMapper: Mapper<MarkerEntity, MarkerDto> {

    private lateinit var documentId: String

    override fun getEntityToDto(entity: MarkerEntity): MarkerDto {
        return MarkerDto(
            documentId,
            entity.getPoint(),
            entity.getNameLocation(),
            entity.getInformationLocation(),
            entity.getAddress()
        )
    }

    override fun getDtoToEntity(dto: MarkerDto): MarkerEntity {
        return MarkerEntity(
            dto.getPoint(),
            dto.getNameLocation(),
            dto.getInformationLocation(),
            dto.getAddress()
        )
    }

    fun setDocumentId(documentId: String): MarkerMapper{
        this.documentId = documentId
        return this
    }
}