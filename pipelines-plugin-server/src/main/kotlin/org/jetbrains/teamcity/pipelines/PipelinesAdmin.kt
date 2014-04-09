package org.jetbrains.teamcity.pipelines

import jetbrains.buildServer.web.openapi.PagePlaces
import jetbrains.buildServer.web.openapi.PluginDescriptor
import jetbrains.buildServer.serverSide.SBuildServer
import jetbrains.buildServer.web.openapi.WebControllerManager
import jetbrains.buildServer.controllers.admin.projects.EditProjectTab
import javax.servlet.http.HttpServletRequest
import jetbrains.buildServer.controllers.FormUtil
import jetbrains.buildServer.controllers.BaseFormXmlController
import javax.servlet.http.HttpServletResponse
import org.springframework.web.servlet.ModelAndView
import org.jdom.Element
import jetbrains.buildServer.web.openapi.ControllerAction

/**
 * Created by Nikita.Skvortsov
 * date: 09.04.2014.
 */

open class ProjectPipelinesAdminTab(pagePlaces: PagePlaces,
                                    descriptor: PluginDescriptor,
                                    server: SBuildServer,
                                    controllerManager: WebControllerManager): EditProjectTab(pagePlaces, "pipelines-admin",
        descriptor.getPluginResourcesPath("pipelines-admin.jsp"), "Pipelines settings") {

    val adminController: ProjectPipelinesAdminController

    {
        adminController = ProjectPipelinesAdminController(server, controllerManager)
        addCssFile(descriptor.getPluginResourcesPath("css/pipelines.css"))
        addJsFile(descriptor.getPluginResourcesPath("js/pipelines-admin.js"))
    }


    override fun fillModel(model: MutableMap<String, Any>, request: HttpServletRequest) {
        super<EditProjectTab>.fillModel(model, request)

        val pipelinesSettingsForm = getForm(request)
        if (pipelinesSettingsForm != null) {
            model["pipelines"] = PipelinesAdminBean(pipelinesSettingsForm)
        }
    }

    fun getForm(request: HttpServletRequest): PipelinesSettingsForm? {
        val project = getProject(request)
        if (project == null ) {
            return null
        }
        return FormUtil.getOrCreateForm(request, javaClass<PipelinesSettingsForm>(), project.getExternalId() + "_pipes", { PipelinesSettingsForm(project) })
    }

}


class ProjectPipelinesAdminController(private val server: SBuildServer,
                                      private val controllerManager: WebControllerManager): BaseFormXmlController(server) {
    {
        controllerManager.registerController("/pipelineAdminCtrl.html", this)
        controllerManager.registerAction(this, AddPipelineAction())
        controllerManager.registerAction(this, EditStepAction())
    }

    override fun doGet(request: HttpServletRequest, response: HttpServletResponse): ModelAndView? {
        throw UnsupportedOperationException()
    }

    override fun doPost(request: HttpServletRequest, response: HttpServletResponse, xmlResponse: Element) {
        val action = controllerManager.getAction(this, request)
        action?.process(request, response, xmlResponse)
    }

    fun getForm(request: HttpServletRequest, projectExtId: String): PipelinesSettingsForm? {
        val project = server.getProjectManager().findProjectByExternalId(projectExtId)
        if (project == null ) {
            return null
        }
        return FormUtil.getOrCreateForm(request, javaClass<PipelinesSettingsForm>(), project.getExternalId() + "_pipes", { PipelinesSettingsForm(project) })
    }

    inner class AddPipelineAction(): NamedAction("newPipeline") {
        override fun process(request: HttpServletRequest, response: HttpServletResponse, xmlResponse: Element?) {
            val newPipelineForm = FormUtil.bindFromRequest(request, NewPipelineForm())!!.getTarget() as NewPipelineForm
            val pipelinesSettingsForm = getForm(request, newPipelineForm.projectExtId)
            val newName = newPipelineForm.newName
            pipelinesSettingsForm?.addPipeline(Pipeline("id_${newName}", newName))
        }
    }

    inner class EditStepAction(): NamedAction("editStep") {
        override fun process(request: HttpServletRequest, response: HttpServletResponse, ajaxResponse: Element?) {
            val addStepForm = FormUtil.bindFromRequest(request, AddStepForm())!!.getTarget() as AddStepForm
            val form = getForm(request, addStepForm.projectExtId)
            if (form != null) {
                form[addStepForm.pipeId]?.steps?.add(addStepForm.stepName)
            }
        }
    }
}

abstract class NamedAction(val actionName: String): ControllerAction {
    override fun canProcess(request: HttpServletRequest): Boolean {
        return request.getParameter(actionName) != null
    }
}

open class FormWithProjectId(var projectExtId: String = "")
open class NewPipelineForm(var newName: String = "") : FormWithProjectId()
open class AddStepForm(var pipeId: String = "", var stepName: String = "") : FormWithProjectId()