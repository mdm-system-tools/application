package org.MdmSystemTools.Application

import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import java.io.IOException
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.runBlocking
import org.MdmSystemTools.Application.model.entity.AppDatabase
import org.MdmSystemTools.Application.model.entity.Associate
import org.MdmSystemTools.Application.model.entity.AssociateDao
import org.MdmSystemTools.Application.model.entity.Grupo
import org.MdmSystemTools.Application.model.entity.GrupoDao
import org.MdmSystemTools.Application.model.entity.Project
import org.MdmSystemTools.Application.model.entity.ProjectDao
import org.MdmSystemTools.Application.model.entity.ProjectWithGroupsDao
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DatabaseAssociateUnitTest {
  @get:Rule val composeTestRule = createComposeRule()

  private lateinit var db: AppDatabase
  private lateinit var associateDao: AssociateDao
  private lateinit var groupDao: GrupoDao
  private lateinit var projectDao: ProjectDao
  private lateinit var projectWithGroupsDao: ProjectWithGroupsDao

  @Before
  fun setup() {
    val context = ApplicationProvider.getApplicationContext<Context>()
    db =
      Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java)
        .allowMainThreadQueries()
        .build()
    associateDao = db.associateDao()
    projectDao = db.projectDao()
    groupDao = db.groupDao()
    projectWithGroupsDao = db.projectWithGroups()
  }

  @After
  @Throws(IOException::class)
  fun tearDown() {
    db.close()
  }

  @Test
  @Throws(Exception::class)
  fun crudAssociateTest() = runBlocking {
    val newAssociate = Associate(numberCard = "123", name = "Maria", groupId = 1)
    associateDao.insert(newAssociate)
    var associate = associateDao.getByid("123")

    assertEquals(associate.name, "Maria")

    associate.name = "jose"

    associateDao.updateAssociate(associate)

    associate = associateDao.getByid("123")

    assertEquals(associate.name, "jose")

    associateDao.delete(associate)

    assertEquals(associateDao.getAll().size, 0)
  }

  @Test
  fun crudGroupTest() = runBlocking {
    val projectId =
      projectDao.insert(Project(name = "Projeto A", region = "Sul", value = 5000)).toInt()
    groupDao.insert(Grupo(schedule = "9:00", projectId = projectId))
    groupDao.insert(Grupo(schedule = "10:00", projectId = projectId))

    val projectWithGroups = projectWithGroupsDao.getProjectWithGroups(projectId)
    assertEquals(projectWithGroups.project.name, "Projeto A")
    assertEquals(projectWithGroups.groups.first().schedule, "9:00")
  }
}
