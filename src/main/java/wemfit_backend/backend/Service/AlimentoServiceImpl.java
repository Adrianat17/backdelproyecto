package wemfit_backend.backend.Service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import wemfit_backend.backend.Exceptions.AlimentoNotFoundException;
import wemfit_backend.backend.Model.Alimento;
import wemfit_backend.backend.Repository.AlimentoRepository;
import wemfit_backend.backend.dto.AlimentoRequest;
import wemfit_backend.backend.dto.AlimentoResponse;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
@Service
@AllArgsConstructor
public class AlimentoServiceImpl implements AlimentoService {

    private final AlimentoRepository alimentoRepository;

    public void deleteAlimento(Long id) {
        if (!alimentoRepository.existsById(id)) {
            throw new AlimentoNotFoundException();
        }
        alimentoRepository.deleteById(id);
    }

    @Override
    public List<AlimentoResponse> getAllAlimentos() {
        List<Alimento> alimentos = alimentoRepository.findAll();
        List<AlimentoResponse> responseList = new ArrayList<>();

        for (Alimento alimento : alimentos) {
            responseList.add(toAlimentoResponse(alimento));
        }

        return responseList;
    }

    @Override
    public AlimentoResponse getAlimentoById(Long id) {
        Alimento alimento = alimentoRepository.findById(id)
                .orElseThrow(AlimentoNotFoundException::new);
        return this.toAlimentoResponse(alimento);
    }

    @Override
    public List<String> getCategoriasByGrupoAlimento(String grupoAlimento) {
        List<Alimento> alimentos = alimentoRepository.findByGrupoAlimento(grupoAlimento);
        Set<String> categoriasSet = new HashSet<>();

        for (Alimento alimento : alimentos) {
            categoriasSet.add(alimento.getCategoria());
        }

        return new ArrayList<>(categoriasSet);
    }

    @Override
    public List<AlimentoResponse> getAlimentosByGrupoAndCategoria(String grupoAlimento, String categoria) {
        List<Alimento> alimentos = alimentoRepository.findByCategoriaAndGrupoAlimento(categoria, grupoAlimento);
        List<AlimentoResponse> responseList = new ArrayList<>();

        for (Alimento alimento : alimentos) {
            responseList.add(toAlimentoResponse(alimento));
        }

        return responseList;
    }

    /*
    private Alimento toAlimento(AlimentoRequest alimentoRequest) {
        Alimento alimento = new Alimento();
        alimento.setCategoria(alimentoRequest.getCategoria());
        alimento.setAlimento(alimentoRequest.getAlimento());
        alimento.setGrupoAlimento(alimentoRequest.getGrupoAlimento());
        alimento.setCalorias(alimentoRequest.getCalorias());
        return alimento;
    }
*/
    private AlimentoResponse toAlimentoResponse(Alimento alimento) {
        AlimentoResponse alimentoResponse = new AlimentoResponse();
        alimentoResponse.setCategoria(alimento.getCategoria());
        alimentoResponse.setId(alimento.getId());
        alimentoResponse.setAlimento(alimento.getAlimento());
        alimentoResponse.setGrupoAlimento(alimento.getGrupoAlimento());
        alimentoResponse.setCalorias(alimento.getCalorias());
        return alimentoResponse;
    }

    @Override
    public List<String> getAllCategorias() {
        List<Alimento> alimentos = alimentoRepository.findAll();
        Set<String> categoriasSet = new HashSet<>();

        for (Alimento alimento : alimentos) {
            categoriasSet.add(alimento.getCategoria());
        }

        return new ArrayList<>(categoriasSet);
    }

    @Override
    public List<String> getAllGruposAlimento() {
        List<Alimento> alimentos = alimentoRepository.findAll();
        Set<String> gruposSet = new HashSet<>();

        for (Alimento alimento : alimentos) {
            gruposSet.add(alimento.getGrupoAlimento());
        }

        return new ArrayList<>(gruposSet);
    }
}