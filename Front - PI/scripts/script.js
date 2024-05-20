const formulario = document.querySelector(".campos_funcn");
var InputNomeFuncionario = document.querySelector("#nome-funcionario");
var InputHorarioEntrada = document.querySelector("#horario-entrada");
var InputCargoFuncionario = document.querySelector("#cargo-funcionario");
var InputHorarioSaida = document.querySelector("#horario-saida");

console.log(InputNomeFuncionario);
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

formulario[0].addEventListener('submit', function (event){
    event.preventDefault();
    console.log(InputNomeFuncionario.value);
    //cadastrarFuncionario();
});