package com.jbarros.permissionanalysis

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jbarros.permissionanalysis.data.local.*
import com.jbarros.permissionanalysis.data.model.ApplicationEntity
import com.jbarros.permissionanalysis.data.model.ApplicationPermissionEntity
import com.jbarros.permissionanalysis.data.model.PermissionAnalysisEntity
import com.jbarros.permissionanalysis.data.model.PermissionEntity
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class ApplicationDatabaseInstrumentedTest {
    private lateinit var applicationDao: ApplicationDao
    private lateinit var permissionAnalysisDao: PermissionAnalysisDao
    private lateinit var permissionDao: PermissionDao
    private lateinit var applicationPermissionDao: ApplicationPermissionDao
    private lateinit var db: ApplicationDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, ApplicationDatabase::class.java).build()
        applicationDao = db.applicationDao()
        permissionAnalysisDao = db.permissionAnalysisDao()
        permissionDao = db.permissionDao()
        applicationPermissionDao = db.applicationPermissionDao()

        val applicationEntity = ApplicationEntity(
            uniqueUserId = "123", // Proporciona valores adecuados para la prueba
            packageName = "package.name",
            appName = "Teléfono",
            createdAt = "2023-01-01T12:00:00Z",
            apkPath = "/path/to/apk"
        )
        applicationDao.insert(applicationEntity)

        // Repite el proceso para cualquier otra entidad que necesites como parte de las claves foráneas.
        // Por ejemplo:
        val permissionEntity = PermissionEntity(
            name = "CAMERA",
            description = "Access to camera"
        )
        permissionDao.insert(permissionEntity)
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeApplicationAndReadAll() {
        // Creamos objeto
        val applicationEntity = ApplicationEntity(
            uniqueUserId = "123", // Debes proporcionar un valor adecuado
            packageName = "package.name",
            appName = "Teléfono",
            createdAt = "2023-01-01T12:00:00Z", // Usa el formato de fecha apropiado
            apkPath = "/path/to/apk"
        )

        // Insertamos en bd
        //applicationDao.insert(application = applicationEntity)

        // Leemos todas las applications
        val applications = applicationDao.getAll()

        // Asumimos que queremos testear que se insertó correctamente
        val retrievedApp = applications.firstOrNull { it.packageName == applicationEntity.packageName }

        // Aseguramos que la aplicación se recuperó correctamente
        assertNotNull(retrievedApp) // Primero aseguramos que el objeto recuperado no es nulo
        retrievedApp?.let {
            // Comparamos los campos del objeto recuperado con el original
            assertEquals(it.uniqueUserId, applicationEntity.uniqueUserId)
            assertEquals(it.packageName, applicationEntity.packageName)
            assertEquals(it.appName, applicationEntity.appName)
            assertEquals(it.createdAt, applicationEntity.createdAt)
            assertEquals(it.apkPath, applicationEntity.apkPath)
        }

        // Borramos el objeto
        applicationDao.delete(application = applicationEntity)

        // Aseguramos que el objeto fue borrado
        assertTrue(applicationDao.getOne(applicationEntity.uid).isEmpty())
    }

    @Test
    @Throws(Exception::class)
    fun writeApplicationPermissionAndReadAll() {
        //Creamos objeto ApplicationPermissionEntity
        val appPermission = ApplicationPermissionEntity(
            applicationUid = 1,
            permissionId = 1,
            isGranted = true,
            createdAt = "2021-01-01T12:00:00Z",
            updatedAt = "2021-01-02T12:00:00Z"
        )

        //Insertamos en bd
        applicationPermissionDao.insert(appPermission)

        //Leemos todos los ApplicationPermission
        val appPermissions = applicationPermissionDao.getAll()

        //Aseguramos que la inserción es correcta
        assertEquals(appPermissions[0].applicationUid, appPermission.applicationUid)
        assertEquals(appPermissions[0].permissionId, appPermission.permissionId)
        assertTrue(appPermissions[0].isGranted)
        assertEquals(appPermissions[0].createdAt, appPermission.createdAt)
        assertEquals(appPermissions[0].updatedAt, appPermission.updatedAt)
        applicationPermissionDao.delete(appPermission)

    }

    @Test
    @Throws(Exception::class)
    fun writePermissionAnalysisAndReadAll() {
        // Suponiendo que ya tenemos una ApplicationEntity con uid = 1
        // Creamos el objeto PermissionAnalysisEntity
        val analysis = PermissionAnalysisEntity(applicationUid = 1, createdAt = "2021-01-01", riskScore = 5)

        // Insertamos el objeto en la BD
        permissionAnalysisDao.insert(analysis)

        // Leemos todos los análisis
        val analyses = permissionAnalysisDao.getAll()

        // Validamos que los datos sean correctos
        assertTrue(analyses.any {
            it.applicationUid == analysis.applicationUid &&
                    it.createdAt == analysis.createdAt &&
                    it.riskScore == analysis.riskScore
        })
        permissionAnalysisDao.delete(analysis)
    }

    @Test
    @Throws(Exception::class)
    fun writePermissionAndReadAll() {
        // Creamos el objeto PermissionEntity
        val permission = PermissionEntity(name = "CAMERA", description = "Access to camera", constantName = "ACCESS_LOCATION_EXTRA_COMMANDS")

        // Insertamos el objeto en la BD
        permissionDao.insert(permission)

        val permissionId = permissionDao.getIdByName("ACCESS_LOCATION_EXTRA_COMMANDS")

        // Leemos todos los permisos
        val permissions = permissionDao.getAll()

        // Validamos que los datos sean correctos
        assertTrue(permissions.any { it.name == permission.name && it.description == permission.description})
        permissionDao.delete(permission)
    }
}