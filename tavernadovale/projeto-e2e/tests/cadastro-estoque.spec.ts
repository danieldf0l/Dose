// Arquivo: tests/cadastro-estoque.spec.ts

import { test, expect } from '@playwright/test';

// Define a URL da página de estoque
const ESTOQUE_PAGE_URL = 'http://localhost:8080/estoque.html'; 

test.describe('Caminho Feliz 2: Cadastro de Estoque', () => {

  test('Deve cadastrar um novo registro de estoque para um EAN existente e confirmar o sucesso (alerta)', async ({ page }) => {
    
    // --- Dados de Teste ---
    // EAN GARANTIDO NO BANCO: 
    const EAN_EXISTENTE = '7894900027013'; 
    const quantidade = '50';
    // Define uma data de validade futura no formato YYYY-MM-DD
    const dataValidade = '2028-12-31'; 
    const numeroLote = `LOTE-${Date.now()}`; 
    
    // --- 1. Acessar a página de estoque ---
    await page.goto(ESTOQUE_PAGE_URL); 

    // --- 2. Manipular o Alerta de Sucesso (Assertiva) ---
    let mensagemSucessoRecebida = false;
    const MENSAGEM_SUCESSO = 'Registro de Estoque cadastrado com sucesso!';
    
    // Configura o listener para interceptar o alert() do JS
    page.on('dialog', async dialog => {
      if (dialog.message() === MENSAGEM_SUCESSO) {
        mensagemSucessoRecebida = true;
      }
      // Fecha o diálogo para que o script continue
      await dialog.accept(); 
    });


    // --- 3. Preencher o formulário usando os IDs identificados ---
    
    // Código de Barras do Produto: #codigo-estoque
    await page.fill('#codigo-estoque', EAN_EXISTENTE);
    
    // Quantidade: #quatidade-estoque
    await page.fill('#quatidade-estoque', quantidade);
    
    // Data de Validade: #data-estoque (Input type="date" precisa de YYYY-MM-DD)
    await page.fill('#data-estoque', dataValidade);
    
    // Lote: #lote-estoque
    await page.fill('#lote-estoque', numeroLote); 

    // --- 4. Clicar no botão Salvar ---
    // Botão: .btn_slv_estq
    await page.click('.btn_slv_estq'); 
    
    // --- 5. Verificação (Assertiva) de Sucesso ---
    
    // Aguarda o processamento do backend e do alert
    await page.waitForTimeout(500); 

    // Confirma que o alerta de sucesso foi recebido
    expect(mensagemSucessoRecebida).toBe(true);
    
    // --- 6. Verificação Adicional (Opcional, mas Recomendada para E2E) ---
    // Se houver uma tabela/lista que mostre o estoque recém-adicionado na mesma tela, 
    // você pode adicionar uma verificação como:
    // await expect(page.locator(`text=${numeroLote}`)).toBeVisible(); 

  });
});