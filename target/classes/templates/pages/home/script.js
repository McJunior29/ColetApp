// Dados de exemplo para as denúncias
const denuncias = [
  {
    imagem:
      "https://s2.glbimg.com/bdYc7aMeFx6bxllt4F9FWJVbeMM=/s.glbimg.com/jo/g1/f/original/2017/05/24/foto_divulgacao_2.jpg",
    descricao:
      "Nunca produzimos tanto lixo como agora. Cada brasileiro gera, em média, 387 quilos de resíduos por ano, quantidade similar à de países mais desenvolvidos como Croácia e Hungria. No entanto, apenas 58% desse lixo é destinado corretamente. Nunca produzimos tanto lixo como agora. Cada brasileiro gera, em média, 387 quilos de resíduos por ano, quantidade similar à de países mais desenvolvidos como Croácia e Hungria. No entanto, apenas 58% desse lixo é destinado corretamente. Nunca produzimos tanto lixo como agora. Cada brasileiro gera, em média, 387 quilos de resíduos por ano, quantidade similar à de países mais desenvolvidos como Croácia e Hungria. No entanto, apenas 58% desse lixo é destinado corretamente. Nunca produzimos tanto lixo como agora. Cada brasileiro gera, em média, 387 quilos de resíduos por ano, quantidade similar à de países mais desenvolvidos como Croácia e Hungria. No entanto, apenas 58% desse lixo é destinado corretamente.",
  },
  {
    imagem:
      "https://s2.glbimg.com/bdYc7aMeFx6bxllt4F9FWJVbeMM=/s.glbimg.com/jo/g1/f/original/2017/05/24/foto_divulgacao_2.jpg",
    descricao: "Nunca produzimos tanto lixo como agora. Cada brasileiro gera, em média, 387 quilos de resíduos por ano, quantidade similar à de países mais desenvolvidos como Croácia e Hungria. No entanto, apenas 58% desse lixo é destinado corretamente. Nunca produzimos tanto lixo como agora. Cada brasileiro gera, em média, 387 quilos de resíduos por ano, quantidade similar à de países mais desenvolvidos como Croácia e Hungria. No entanto, apenas 58% desse lixo é destinado corretamente. Nunca produzimos tanto lixo como agora. Cada brasileiro gera, em média, 387 quilos de resíduos por ano, quantidade similar à de países mais desenvolvidos como Croácia e Hungria. No entanto, apenas 58% desse lixo é destinado corretamente. Nunca produzimos tanto lixo como agora. Cada brasileiro gera, em média, 387 quilos de resíduos por ano, quantidade similar à de países mais desenvolvidos como Croácia e Hungria. No entanto, apenas 58% desse lixo é destinado corretamente.",
  },
  {
    imagem:
      "https://s2.glbimg.com/bdYc7aMeFx6bxllt4F9FWJVbeMM=/s.glbimg.com/jo/g1/f/original/2017/05/24/foto_divulgacao_2.jpg",
    descricao: "Nunca produzimos tanto lixo como agora. Cada brasileiro gera, em média, 387 quilos de resíduos por ano, quantidade similar à de países mais desenvolvidos como Croácia e Hungria. No entanto, apenas 58% desse lixo é destinado corretamente. Nunca produzimos tanto lixo como agora. Cada brasileiro gera, em média, 387 quilos de resíduos por ano, quantidade similar à de países mais desenvolvidos como Croácia e Hungria. No entanto, apenas 58% desse lixo é destinado corretamente. Nunca produzimos tanto lixo como agora. Cada brasileiro gera, em média, 387 quilos de resíduos por ano, quantidade similar à de países mais desenvolvidos como Croácia e Hungria. No entanto, apenas 58% desse lixo é destinado corretamente. Nunca produzimos tanto lixo como agora. Cada brasileiro gera, em média, 387 quilos de resíduos por ano, quantidade similar à de países mais desenvolvidos como Croácia e Hungria. No entanto, apenas 58% desse lixo é destinado corretamente.",
  },
];

// Dados de exemplo para as rotas
const rotas = [
  {
    rua: "Rua A",
    rota: "Rota 1",
    horarios: [
      { dia: "Segunda-feira", horario: "12:00" },
      { dia: "Terça-feira", horario: "14:30" },
      { dia: "Quarta-feira", horario: "11:15" },
    ],
  },
  {
    rua: "Rua B",
    rota: "Rota 2",
    horarios: [
      { dia: "Segunda-feira", horario: "13:45" },
      { dia: "Terça-feira", horario: "16:00" },
      { dia: "Quarta-feira", horario: "10:30" },
    ],
  },
  // Adicione mais rotas aqui conforme necessário
];

// Função para renderizar a lista de denúncias
function renderizarDenuncias() {
  const feedContainer = document.getElementById("feed-container");
  feedContainer.innerHTML = ""; // Limpa o conteúdo atual

  denuncias.forEach((denuncia) => {
    const denunciaDiv = document.createElement("div");
    denunciaDiv.classList.add("denuncia");

    const imagem = document.createElement("img");
    imagem.classList.add("feed-image");
    imagem.src = denuncia.imagem;

    const descricao = document.createElement("p");
    descricao.textContent = denuncia.descricao;

    denunciaDiv.appendChild(imagem);
    denunciaDiv.appendChild(descricao);
    feedContainer.appendChild(denunciaDiv);
  });
}

// Função para renderizar a lista de rotas
function renderizarRotas() {
  const feedContainer = document.getElementById("feed-container");
  feedContainer.innerHTML = ""; 

  rotas.forEach((rota) => {
    const rotaDiv = document.createElement("div");
    rotaDiv.classList.add("rota");

    const titulo = document.createElement("h2");
    titulo.textContent = rota.rua;

    const subtitulo = document.createElement("p");
    subtitulo.textContent = rota.rota;

    const horariosList = document.createElement("ul");
    rota.horarios.forEach((horario) => {
      const horarioItem = document.createElement("li");
      horarioItem.textContent = `${horario.dia}: ${horario.horario}`;
      horariosList.appendChild(horarioItem);
    });

    rotaDiv.appendChild(titulo);
    rotaDiv.appendChild(subtitulo);
    rotaDiv.appendChild(horariosList);

    feedContainer.appendChild(rotaDiv);
  });
}

// Função para inicializar a página com a lista de denúncias
function inicializarPagina() {
  const denunciasButton = document.getElementById("denuncias-button");
  const rotasButton = document.getElementById("rotas-button");

  denunciasButton.style.backgroundColor = "#fff";
  denunciasButton.addEventListener("click", function () {
    // Adiciona a classe "selected" ao botão de Denúncias
    denunciasButton.style.backgroundColor = "#fff";
    rotasButton.style.backgroundColor = "transparent";
    denunciasButton.classList.add("selected");
    // Remove a classe "selected" do botão de Rotas
    rotasButton.classList.remove("selected");

    // Renderiza a lista de denúncias
    renderizarDenuncias();
  });

  rotasButton.addEventListener("click", function () {
    // Adiciona a classe "selected" ao botão de Rotas
    denunciasButton.style.backgroundColor = "transparent";
    rotasButton.style.backgroundColor = "#fff";
    rotasButton.classList.add("selected");
    // Remove a classe "selected" do botão de Denúncias
    denunciasButton.classList.remove("selected");

    // Renderiza a lista de rotas
    renderizarRotas();
  });

  // Inicialmente, renderiza a lista de denúncias
  renderizarDenuncias();
}

// Chama a função para inicializar a página quando a página carregar
window.addEventListener("load", inicializarPagina);
