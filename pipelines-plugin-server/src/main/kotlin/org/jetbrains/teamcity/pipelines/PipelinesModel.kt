package org.jetbrains.teamcity.pipelines

import jetbrains.buildServer.serverSide.SProject
import java.util.ArrayList

/**
 * Created by Nikita.Skvortsov
 * date: 09.04.2014.
 */


open class Pipeline(val id: String, val name: String)

open class PipelineBean(val id: String, val name: String, val count: Int)

open class PipelinesSettingsForm(project: SProject) {

    val pipelines: MutableList<Pipeline> = ArrayList<Pipeline>()
    fun addPipeline(p: Pipeline) {
        pipelines.add(p)
    }
}

open class PipelinesAdminBean(form: PipelinesSettingsForm) {
    val list : List<PipelineBean>
    {
        list = form.pipelines.map { PipelineBean(it.id, it.name, 0) }
    }
}
