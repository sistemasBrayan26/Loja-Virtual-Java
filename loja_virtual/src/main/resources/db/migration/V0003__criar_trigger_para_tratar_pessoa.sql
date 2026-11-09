CREATE OR REPLACE FUNCTION validaChavePessoa() 
    RETURNS TRIGGER 
    LANGUAGE plpgsql
AS $$
BEGIN
    -- Verifica se a chave não existe nem em pessoa_fisica e nem em pessoa_juridica
    IF NOT EXISTS (SELECT 1 FROM pessoa_fisica WHERE id = new.pessoa_id) 
       AND NOT EXISTS (SELECT 1 FROM pessoa_juridica WHERE id = new.pessoa_id) THEN
        RAISE EXCEPTION 'Nao foi encontrado o ID e FK da pessoa para realizar associação do cadastro';
    END IF;
    
    RETURN NEW;
END;
$$;

CREATE OR REPLACE FUNCTION validaChavePessoaFornecedora() 
    RETURNS TRIGGER 
    LANGUAGE plpgsql
AS $$
BEGIN
    -- Verifica se a chave não existe nem em pessoa_fisica e nem em pessoa_juridica
    IF NOT EXISTS (SELECT 1 FROM pessoa_fisica WHERE id = new.pessoa_fornecedora_id) 
       AND NOT EXISTS (SELECT 1 FROM pessoa_juridica WHERE id = new.pessoa_fornecedora_id) THEN
        RAISE EXCEPTION 'Nao foi encontrado o ID e FK da pessoa fornecedora para realizar associação do cadastro';
    END IF;
    
    RETURN NEW;
END;
$$;


CREATE OR REPLACE TRIGGER trg_chave_pessoa_avaliacao_produto
    BEFORE INSERT OR UPDATE 
    ON avaliacao_produto
    FOR EACH ROW 
    EXECUTE FUNCTION validaChavePessoa();
    
CREATE OR REPLACE TRIGGER trg_chave_pessoa_conta_pagar
    BEFORE INSERT OR UPDATE 
    ON conta_pagar
    FOR EACH ROW 
    EXECUTE FUNCTION validaChavePessoa();

CREATE OR REPLACE TRIGGER trg_chave_pessoa_fornecedora_conta_pagar
    BEFORE INSERT OR UPDATE 
    ON conta_pagar
    FOR EACH ROW 
    EXECUTE FUNCTION validaChavePessoaFornecedora();
    
CREATE OR REPLACE TRIGGER trg_chave_pessoa_conta_receber
    BEFORE INSERT OR UPDATE 
    ON conta_receber
    FOR EACH ROW 
    EXECUTE FUNCTION validaChavePessoa();

CREATE OR REPLACE TRIGGER trg_chave_pessoa_endereco
    BEFORE INSERT OR UPDATE 
    ON endereco
    FOR EACH ROW 
    EXECUTE FUNCTION validaChavePessoa();
    
CREATE OR REPLACE TRIGGER trg_chave_pessoa_nota_fiscal_compra
    BEFORE INSERT OR UPDATE 
    ON nota_fiscal_compra
    FOR EACH ROW 
    EXECUTE FUNCTION validaChavePessoa();

CREATE OR REPLACE TRIGGER trg_chave_pessoa_usuario
    BEFORE INSERT OR UPDATE 
    ON usuario
    FOR EACH ROW 
    EXECUTE FUNCTION validaChavePessoa();

