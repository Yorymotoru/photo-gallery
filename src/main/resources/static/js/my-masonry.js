const padding = 15;
const columnsLeft = [0, 34, 68];

let columns = [0, 0, 0];
let timeOut = null;

let myMasonry = function () {
    let grid = document.querySelector(".grid");
    let gridItems = grid.getElementsByClassName("grid-item");

    let gridItemWidth = grid.offsetWidth / 100 * 32;

    for (let i = 0; i < gridItems.length; i++) {
        let it = gridItems.item(i);

        if (it.getAttribute("data-checked") !== "true") {
            let h = it.getAttribute("data-height");
            let w = it.getAttribute("data-width");

            let newHeight = h * gridItemWidth / w;
            let minColumn = Math.min(columns[0], columns[1], columns[2]);
            let currColumn = columns.findIndex(value => value === minColumn);

            it.setAttribute("style",
                "position: absolute; left: " + columnsLeft[currColumn] + "%; top: " + columns[currColumn] + "px;"
            );
            it.setAttribute("data-checked", "true");
            columns[currColumn] += newHeight + padding;

            grid.setAttribute("style",
                "height:" + Math.max(columns[0], columns[1], columns[2]) + "px;"
            );
        }
    }
}

myMasonry.appended = function () {
    myMasonry()
}

myMasonry.resized = function () {
    if (timeOut != null)
        clearTimeout(timeOut);

    timeOut = setTimeout(function(){
        console.log("Resized")
        columns = [0, 0, 0];
        let grid = document.querySelector(".grid");
        let gridItems = grid.getElementsByClassName("grid-item");
        for (let i = 0; i < gridItems.length; i++) {
            gridItems.item(i).setAttribute("data-checked", "false");
        }
        myMasonry()
    }, 100);
}