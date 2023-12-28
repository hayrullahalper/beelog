import "./style.css";

let count = 0;
let step = 1;
const counter = document.getElementById("count");
const increase = document.getElementById("increase");
const reset = document.getElementById("reset");
const double = document.getElementById("double")


const render = () => {
    counter.innerHTML = count;
}

render();

double.addEventListener("click", (event) => {
    const checked = event.target.checked;
    if (checked) {
        step = 2;
        return;
    }

    step = 1;
})

increase.addEventListener("click", () => {
    count += step;
    render();
})

reset.addEventListener("click", () => {
    count = 0;
    render();
})
