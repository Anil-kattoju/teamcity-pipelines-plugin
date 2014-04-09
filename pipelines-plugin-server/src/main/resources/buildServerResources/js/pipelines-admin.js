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
    }
}));