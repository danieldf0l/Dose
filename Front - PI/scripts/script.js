const formulario = document.querySelector(".campos_funcn");
var InputNomeFuncionario = document.querySelector("#nome-funcionario");
var InputHorarioEntrada = document.querySelector("#horario-entrada");
var InputCargoFuncionario = document.querySelector("#cargo-funcionario");
var InputHorarioSaida = document.querySelector("#horario-saida");

console.log(InputNomeFuncionario);
formulario.addEventListener("submit", (event) => {
    //event.preventDefault();
    fetch("http://localhost:8080/funcionario/submit", 
    {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        method: "POST",
        body: JSON.stringify({
            nomeFuncionario: InputNomeFuncionario.value,
            horarioEntrada: InputHorarioEntrada.value,
            cargoFuncionario: InputCargoFuncionario.value,
            horarioSaida: InputHorarioSaida.value
        })
    })
    .then(function (res) {console.log(res)})
    .catch(function (res) {console.log(res)})
    alert("enviou hein")
})