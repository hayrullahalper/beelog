import "./style.css";

let count = 0;
const counter = document.getElementById("count");
const increase = document.getElementById("increase");
const reset = document.getElementById("reset");

const render = () => {
    counter.innerHTML = count;
}

render();

increase.addEventListener("click", () => {
    count += 1;
    render();
})

reset.addEventListener("click", () => {
    count = 0;
    render();
})
