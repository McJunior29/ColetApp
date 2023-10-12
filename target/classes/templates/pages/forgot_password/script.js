// Obtém os elementos do DOM
const emailContainer = document.querySelector(".email-container");
const pinContainer = document.querySelector(".recovery-code-container");
const newPasswordContainer = document.querySelector(".new-password-container");
const sendCodeButton = document.getElementById("send-code-button");
const verifyCodeButton = document.getElementById("verify-code-button");
const setNewPasswordButton = document.getElementById("set-new-password-button");
const emailInput = document.getElementById("email");
const recoveryCodeInput = document.getElementById("recovery-code-input");
const newPasswordInput = document.getElementById("new-password");
const confirmNewPasswordInput = document.getElementById("confirm-new-password");

// Regex para validar o formato de email
const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,4}$/;

// Regex para validar um código de 8 dígitos
const codeRegex = /^\d{8}$/;

// Adiciona um ouvinte de evento para o botão "Enviar Código"
sendCodeButton.addEventListener("click", function () {
    // Valida o formato de email
    if (!emailRegex.test(emailInput.value)) {
        alert("Por favor, insira um email válido.");
        return;
    }

    // Esconde o contêiner de e-mail
    emailContainer.style.display = "none";
    // Mostra o contêiner de código PIN
    pinContainer.style.display = "block";
});

// Adiciona um ouvinte de evento para o botão "Verificar"
verifyCodeButton.addEventListener("click", function () {
    // Valida o formato do código de recuperação
    if (!codeRegex.test(recoveryCodeInput.value)) {
        alert("Por favor, insira um código de recuperação válido com 8 dígitos.");
        return;
    }

    // Esconde o contêiner de código PIN
    pinContainer.style.display = "none";
    // Mostra o contêiner de nova senha
    newPasswordContainer.style.display = "block";
});

// Adiciona um ouvinte de evento para o botão "Definir Nova Senha"
setNewPasswordButton.addEventListener("click", function () {
    // Validação da nova senha
    const newPassword = newPasswordInput.value;
    const confirmNewPassword = confirmNewPasswordInput.value;

    if (newPassword.length < 8) {
        alert("A nova senha deve conter pelo menos 8 caracteres.");
        return;
    }

    if (newPassword !== confirmNewPassword) {
        alert("As senhas não coincidem. Por favor, tente novamente.");
        return;
    }

    // Implemente aqui a lógica para definir a nova senha
    // Após definir a senha, você pode redirecionar o usuário ou tomar outras ações necessárias
});
