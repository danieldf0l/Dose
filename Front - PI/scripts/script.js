const formulario = document.querySelector("form");
const InputNomeFuncionario = document.getElementById(".nome-funcionario");
const InputHorarioEntrada = document.querySelector(".horario-entrada");
const InputCargoFuncionario = document.querySelector(".cargo-funcionario");
const InputHorarioSaida = document.querySelector(".horario-saida");

console.log(formulario);    
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