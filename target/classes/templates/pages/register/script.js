// Obtém o formulário de cadastro
const signupForm = document.getElementById("signup-form");

// Adiciona um ouvinte de evento para o envio do formulário de cadastro
signupForm.addEventListener("submit", function (event) {
    // Obtém os valores dos campos do formulário
    const nome = document.getElementById("nome").value.trim();
    const email = document.getElementById("email").value.trim();
    const rua = document.getElementById("rua").value.trim();
    const bairro = document.getElementById("bairro").value.trim();
    const numero = document.getElementById("numero").value.trim();
    const senha = document.getElementById("senha").value;
    const confirmarSenha = document.getElementById("confirmar-senha").value;

    // Obtém o elemento de mensagem de erro
    const errorMessage = document.getElementById("error-message");

    // Define uma variável para rastrear se houve algum erro
    let hasError = false;

    // Limpa mensagens de erro anteriores
    errorMessage.textContent = "";

    // Validações
    if (!nome.includes(" ")) {
        errorMessage.textContent = "Por favor, insira seu nome completo (primeiro nome e sobrenome).";
        hasError = true;
    }

    if (email === "" || rua === "" || bairro === "" || numero === "" || senha === "" || confirmarSenha === "") {
        errorMessage.textContent = "Por favor, preencha todos os campos.";
        hasError = true;
    }

    // Validação de e-mail com regex
    const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;
    if (!emailRegex.test(email)) {
        errorMessage.textContent = "Por favor, insira um endereço de e-mail válido.";
        hasError = true;
    }

    if (senha !== confirmarSenha) {
        errorMessage.textContent = "As senhas não coincidem.";
        hasError = true;
    }

    // Se houver erro, impede o envio do formulário
    if (hasError) {
        event.preventDefault();
    }
});
