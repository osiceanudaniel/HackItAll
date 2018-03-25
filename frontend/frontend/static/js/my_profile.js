function setValue() {
    var selectOptions = document.getElementById("ddlViewBy");
    var selected = selectOptions.options[selectOptions.selectedIndex].value;
    document.getElementById("selectedCategory").value = selected;
}