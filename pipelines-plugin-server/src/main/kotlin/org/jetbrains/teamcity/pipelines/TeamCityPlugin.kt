package org.jetbrains.teamcity.pipelines

import jetbrains.buildServer.web.openapi.project.ProjectTab
import jetbrains.buildServer.web.openapi.PagePlaces
import jetbrains.buildServer.serverSide.ProjectManager
import jetbrains.buildServer.web.openapi.PluginDescriptor
import javax.servlet.http.HttpServletRequest
import jetbrains.buildServer.serverSide.SProject
import jetbrains.buildServer.users.SUser

/**
 * Created by Nikita.Skvortsov
 * date: 07.04.2014.
 */

open class ProjectPipelinesTab(pagePlaces: PagePlaces,
                               projectManager: ProjectManager,
                               descriptor: PluginDescriptor) : ProjectTab("piplines-overview", "Pipelines",
        pagePlaces, projectManager, descriptor.getPluginResourcesPath("pipelines-overview.jsp")) {

    override fun fillModel(model: MutableMap<String, Any>, request: HttpServletRequest, project: SProject, user: SUser?) {
        model["tempKey"] = "Hello World!"
    }
}