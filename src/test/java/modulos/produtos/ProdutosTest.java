package modulos.produtos;

import org.junit.jupiter.api.*;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import paginas.LoginPage;
import java.time.Duration;

@DisplayName("Testes Web do Módulo de Produtos")
public class ProdutosTest {

    private WebDriver navegador;

    @BeforeEach
    public void beforeEach() {
        System.setProperty("webdriver.edge.driver", "C:\\Drivers\\edgedriver_win64\\msedgedriver.exe");
        navegador = new EdgeDriver();
        navegador.manage().window().maximize();
        navegador.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        navegador.get("http://165.227.93.41/lojinha-web/v2/");
    }

    @Test
    @DisplayName("Não é Permitido Registrar Produto com Valor Igual a Zero")
    public void testNaoEPermitidoRegistrarProdutoComValorIgualAZero() {
        String mensagemApresentada = new LoginPage(navegador)
                .informarUsuario("jessieferraz")
                .informarSenha("123456")
                .submeterFormularioLogin()
                .acessarFormularioAdicaoNovoProduto()
                .informarNomeDoProduto("Iphone")
                .informarValorDoProduto("000")
                .informarCoresDoProduto("preto,branco")
                .submeterFormularioDeAdicaoComErro()
                .capturarMensagemApresentada();

        Assertions.assertEquals("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00", mensagemApresentada);
    }

    @Test
    @DisplayName("Não é Permitido Registrar Produto com Valor Acima de Sete Mil")
    public void testNaoEPermitidoRegistarProdutoComValorAcimaDeSeteMil(){
        String mensagemApresentada = new LoginPage(navegador)
                .informarUsuario("jessieferraz")
                .informarSenha("123456")
                .submeterFormularioLogin()
                .acessarFormularioAdicaoNovoProduto()
                .informarNomeDoProduto("PlayStation 5")
                .informarValorDoProduto("700001")
                .informarCoresDoProduto("preto,branco")
                .submeterFormularioDeAdicaoComErro()
                .capturarMensagemApresentada();

        Assertions.assertEquals("O valor do produto deve estar entre R$ 0,01 e R$ 7.000,00", mensagemApresentada);

    }

    @Test
    @DisplayName("Posso adicionar produtos no valor de 0,01")
    public void testPertidoAdicionarProdutosComValorDeUmCentavo(){
        String mensagemApresentada = new LoginPage(navegador)
                .informarUsuario("jessieferraz")
                .informarSenha("123456")
                .submeterFormularioLogin()
                .acessarFormularioAdicaoNovoProduto()
                .informarNomeDoProduto("Chinelo")
                .informarValorDoProduto("001")
                .informarCoresDoProduto("preto,branco")
                .submeterFormularioDeAdicaoComSucesso()
                .capturarMensagemApresentada();

        Assertions.assertEquals("Produto adicionado com sucesso", mensagemApresentada);
    }

    @Test
    @DisplayName("Posso adicionar produtos no valor limite de 7000,00")
    public void testPermitidoAdicionarProdutosComValorLimiteDeSeteMil(){
        String mensagemApresentada = new LoginPage(navegador)
                .informarUsuario("jessieferraz")
                .informarSenha("123456")
                .submeterFormularioLogin()
                .acessarFormularioAdicaoNovoProduto()
                .informarNomeDoProduto("Macbook Pro")
                .informarValorDoProduto("700000")
                .informarCoresDoProduto("branco")
                .submeterFormularioDeAdicaoComSucesso()
                .capturarMensagemApresentada();
        Assertions.assertEquals("Produto adicionado com sucesso", mensagemApresentada);
    }

    @AfterEach
    public void afterEach(){
        navegador.quit();
    }
}