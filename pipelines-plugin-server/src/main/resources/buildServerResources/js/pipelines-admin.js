BS.AddPipelineDialog = OO.extend(BS.AbstractWebForm, OO.extend(BS.AbstractModalDialog, {

    getContainer: function() {
        return $('addPipelineFormDialog');
    },

    formElement: function() {
        return $('addPipelineForm');
    },

    show: function() {
        var form = this.formElement();
        Form.reset(form);
        this.showCentered();
    },

    save: function() {

        BS.FormSaver.save(this, this.formElement().action, OO.extend(BS.FormSaver.AbstractSaveListener, {
            onCompleteSave: function(form, resonseXML, booleanHasHandledErrors, responseText) {
                $("pipelinesTableContainer").refresh();
                BS.AddPipelineDialog.close()
            }
        }));

        return false;
    }
}));