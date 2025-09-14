package com.example.myapplication.repository

import com.example.myapplication.model.AssociatedDto

interface ListAssociatedRepository {
  fun getListAssociateds(): List<AssociatedDto>
  fun getAssociated(id: Int): AssociatedDto
}