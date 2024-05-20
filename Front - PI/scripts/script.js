const formulario = document.querySelector("form");
var InputNomeFuncionario = document.getElementById("nome-funcionario");
var InputHorarioEntrada = document.getElementById("horario-entrada");
var InputCargoFuncionario = document.getElementById("cargo-funcionario");
var InputHorarioSaida = document.getElementById("horario-saida");

console.log(InputHorarioSaida);    
function cadastrarFuncionario(){
    fetch("http://localhost:8080/funcionario", 
    {
        headers: {
            'Accept': 'application/json',
            'Content-Type': 'application/json'
        },
        method: "POST",
        body: JSON.stringify({
            nomeFuncionario: InputNomeFuncionario,
            horarioEntrada: InputHorarioEntrada,
            cargoFuncionario: InputCargoFuncionario,
            horarioSaida: InputHorarioSaida
        })
    })
    .then(function (res) {console.log(res)})
    .catch(function (res) {console.log(res)})
};


formulario.addEventListener('submit', function (event){
    event.preventDefault();
    console.log(InputNomeFuncionario.value);
    cadastrarFuncionario();
});