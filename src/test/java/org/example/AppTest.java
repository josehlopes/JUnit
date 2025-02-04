package org.example;


import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {
    private Produto produto;

    @BeforeEach
    public void start() {
        System.out.println("Testes iniciados");
        produto = new Produto("Café", 15.00, 10);
    }

    @AfterEach
    public void end() {
        System.out.println("Testes finalizados");
    }

    @Test
    void MustCreateProductWithValidValues() {
        assertEquals("Café", produto.getNome());
        assertEquals(15.00, produto.getPreco());
        assertEquals(10, produto.getEstoque());
    }

    @Test
    void MustCreateProductWithNegativePrice() {
        Produto produto = new Produto("Café", -15.00, 10);
        assertNotEquals(produto.getPreco() < 0, produto.getPreco());
    }

    @Test
    void MustCreateProductWithNegativeStock() {
        Produto produto = new Produto("Café", 15.00, -5);
        assertNotEquals(produto.getEstoque() < 0, produto.getEstoque());
    }

    @Test
    void ShouldChangeTheNameOfTheProduct() {
        produto.setNome("Café Pilão");
        assertEquals("Café Pilão", produto.getNome());
    }

    @Test
    void ShouldChangeThePriceOfTheProduct() {
        produto.setPreco(50.00);
        assertEquals(50.00, produto.getPreco());
    }

    @Test
    void ShouldIncreaseInventory() {
        produto.aumentarEstoque(5);
        assertEquals(15, produto.getEstoque());
    }

    @Test
    void ShouldChangeThePriceOfTheProductToNegativeValue() {
        produto.setPreco(-100.00);
        assertNotEquals(produto.getPreco() < 0, produto.getPreco());
    }

    @Test
    void ShouldSellSmallerQuantityThanTheInventory() {
        Venda venda = new Venda(produto, 5);
        assertTrue(venda.realizarVenda());
        assertEquals(5, venda.getQuantidadeVendida());
    }

    @Test
    void ShouldSellTheSameAmountAsTheInventory() {
        Venda venda = new Venda(produto, 10);
        assertTrue(venda.realizarVenda());
        assertEquals(10, venda.getQuantidadeVendida());
    }

    @Test
    void ShouldSellBiggerQuantityThanTheInventory() {
        Venda venda = new Venda(produto, 15);
        assertFalse(venda.realizarVenda());
    }

    @Test
    void ThisShouldReturnTheFullAmountOfTheSale() {
        Venda venda = new Venda(produto, 2);
        venda.realizarVenda();
        assertEquals(30.00, venda.getTotalVenda());
    }

    @Test
    void ShouldIncreaseInventoryAfterTheSale() {
        Venda venda = new Venda(produto, 5);
        venda.realizarVenda();
        produto.aumentarEstoque(5);
        assertEquals(10, produto.getEstoque());
    }

    @Test
    void ShouldDecreaseInventoryAfterTheSale() {
        Venda venda = new Venda(produto, 5);
        assertTrue(venda.realizarVenda());
        assertEquals(5, produto.getEstoque());
    }

    @Test
    void ItDoesNotSellNonExistentProduct() {
        Produto produto = null;
        Venda venda = new Venda(null, 5);
        assertNull(venda.getProduto());
    }


    @Test
    void ShouldCreateAndSellWithNegativeQuantity() {
        Produto produto = new Produto("Café", 15, -5);
        Venda venda = new Venda(produto, -5);
        assertEquals(-5, venda.getQuantidadeVendida());
    }


    @Test
    void ShouldChangeTheAfterSalesStockWithInsufficientStock() {
        Produto produto = new Produto("Café", 15.00, 0);
        Venda venda = new Venda(produto, 5);
        assertFalse(venda.realizarVenda());
        assertEquals(0, produto.getEstoque());
    }

    @Test
    void ShouldCreateMultipleProductsWithSharedStockAndMakeYourSales() {
        Produto produto1 = new Produto("Café", 15.00, 10);
        Produto produto2 = new Produto("Açúcar", 5.00, 5);
        Venda venda1 = new Venda(produto1, 3);
        Venda venda2 = new Venda(produto2, 2);

        assertTrue(venda1.realizarVenda());
        assertTrue(venda2.realizarVenda());
        assertEquals(7, produto1.getEstoque());
        assertEquals(3, produto2.getEstoque());
    }

    @Test
    void ShouldCalculateTotalSaleWhenProductPriceIsChangedBeforeSale() {
        Produto produto = new Produto("Café", 15.00, 10);
        produto.setPreco(15.99);
        Venda venda = new Venda(produto, 2);
        venda.realizarVenda();
        assertEquals(31.98, venda.getTotalVenda());
    }

    @Test
    void StockShouldRemainUnchangedWhenInitialStockIsZeroAndSaleAttemptFails() {
        Produto produto = new Produto("Café", 15.00, 0);
        Venda venda = new Venda(produto, 1);
        assertFalse(venda.realizarVenda());
    }

    @Test
    void StockShouldIncreaseAfterRestockingAndSuccessfulSale() {
        Produto produto = new Produto("Café", 15.00, 0);
        produto.aumentarEstoque(10);
        Venda venda = new Venda(produto, 5);
        assertTrue(venda.realizarVenda());
        assertEquals(5, produto.getEstoque());
    }
}
