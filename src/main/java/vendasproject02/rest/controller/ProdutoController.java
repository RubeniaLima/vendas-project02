package vendasproject02.rest.controller;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import vendasproject02.domain.entity.Cliente;
import vendasproject02.domain.entity.Produto;
import vendasproject02.domain.repository.ClienteRepository;
import vendasproject02.domain.repository.ProdutoRepository;

import javax.validation.Valid;
import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("api/produtos")
public class ProdutoController {

    private ProdutoRepository produtoRepository;

    public ProdutoController(ProdutoRepository produtoRepository){
        this.produtoRepository=produtoRepository;
    }

    @GetMapping("{id}")
    public Produto getProdutoById(@PathVariable Integer id){
        return produtoRepository
                .findById(id)
                .orElseThrow(()->new ResponseStatusException(NOT_FOUND,
                "Produto não encontrado"));
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public Produto save (@RequestBody @Valid Produto produto){
      return produtoRepository.save(produto);
    }

    @DeleteMapping ("{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete (@PathVariable Integer id){
       produtoRepository
               .findById(id)
               .map(p -> {
                   produtoRepository.delete(p);
                   return Void.TYPE;
               })
               .orElseThrow(()->new ResponseStatusException(NOT_FOUND,
                       "Produto não encontrado"));
    }

    @PutMapping("{id}")
    @ResponseStatus(NO_CONTENT)
    public void update (@PathVariable Integer id, @RequestBody @Valid Produto produto){
        produtoRepository
                .findById(id)
                .map( clienteExistente->{
            produto.setId(clienteExistente.getId());
                    produtoRepository.save(produto);
            return clienteExistente;

        }).orElseThrow(()-> new ResponseStatusException(NOT_FOUND,
                         "Produto não encontrado"));
    }

    @GetMapping
    public List<Produto> find (Produto filtro){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

        Example example= Example.of(filtro,matcher);
        return produtoRepository.findAll(example);
    }

}
