// Arquivo: tests/cadastro-funcionario.spec.ts

import { test, expect } from '@playwright/test';

// Define a URL da página de funcionários
const FUNCIONARIO_PAGE_URL = 'http://localhost:8080/funcionario.html'; 

test.describe('Caminho Feliz 3: Cadastro de Funcionário', () => {

  test('Deve cadastrar um novo funcionário e receber a confirmação de sucesso (alerta)', async ({ page }) => {
    
    // --- Dados de Teste ---
    // Usamos Date.now() para criar um nome semi-único
    const nomeFuncionario = `Carlos Teste ${Date.now()}`; 
    const cargo = 'Caixa'; // Usando um dos cargos válidos (Gerente, Caixa, Atendente, Faxineiro)
    const horarioEntrada = '08:00'; // Formato HH:mm para input type="time"
    const horarioSaida = '17:00'; 
    
    // --- 1. Acessar a página de funcionários ---
    await page.goto(FUNCIONARIO_PAGE_URL); 

    // --- 2. Manipular o Alerta de Sucesso (Assertiva) ---
    let mensagemSucessoRecebida = false;
    const MENSAGEM_SUCESSO = 'Funcionário cadastrado com sucesso!';
    
    // Configura o listener para interceptar o alert() do JS
    page.on('dialog', async dialog => {
      if (dialog.message() === MENSAGEM_SUCESSO) {
        mensagemSucessoRecebida = true;
      }
      // Fecha o diálogo para que o script continue
      await dialog.accept(); 
    });


    // --- 3. Preencher o formulário usando os IDs identificados ---
    
    // Nome do Funcionário: #nome-funcionario
    await page.fill('#nome-funcionario', nomeFuncionario);
    
    // Cargo: #cargo-funcionario
    await page.fill('#cargo-funcionario', cargo);
    
    // Horário de Entrada: #horario-entrada (Input type="time")
    await page.fill('#horario-entrada', horarioEntrada);
    
    // Horário de Saída: #horario-saida (Input type="time")
    await page.fill('#horario-saida', horarioSaida); 

    // --- 4. Clicar no botão Salvar ---
    // Botão: .btn_slv_funcn
    await page.click('.btn_slv_funcn'); 
    
    // --- 5. Verificação (Assertiva) de Sucesso ---
    
    // Aguarda o processamento do backend e do alert
    await page.waitForTimeout(500); 

    // Confirma que o alerta de sucesso foi recebido
    expect(mensagemSucessoRecebida).toBe(true);

  });
});