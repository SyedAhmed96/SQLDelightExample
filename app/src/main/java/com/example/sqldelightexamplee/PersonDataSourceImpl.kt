package com.example.sqldelightexamplee

import com.squareup.sqldelight.runtime.coroutines.asFlow
import com.squareup.sqldelight.runtime.coroutines.mapToList
import example.persondb.PersonEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class PersonDataSourceImpl(
        db: personDatabase
): PersonDataSource {
        private val queries = db.personEntityQueries


        override suspend fun getPersonById(id: Long): PersonEntity? {
                return withContext(Dispatchers.IO) {
                        queries.getPersonById(id).executeAsOneOrNull()
                }
        }

        override fun getAllPersons(): Flow<List<PersonEntity>> {
                return queries.getAllPersons().asFlow().mapToList()
        }

        override suspend fun deletePersonById(id: Long) {
                withContext(Dispatchers.IO) {
                        queries.deletePerson(id)
                }
        }

        override suspend fun insertPerson(firstName: String, lastName: String, id: Long?) {
                withContext(Dispatchers.IO) {
                        queries.insertPerson(id, firstName, lastName)
                }
        }

}