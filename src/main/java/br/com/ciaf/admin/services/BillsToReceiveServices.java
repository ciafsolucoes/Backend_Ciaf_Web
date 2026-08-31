package br.com.ciaf.admin.services;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import br.com.ciaf.admin.entities.CaixaDiario;
import br.com.ciaf.admin.enums.MovType;
import br.com.ciaf.admin.repositories.CaixaDiarioRepository;
import org.springframework.stereotype.Service;

import br.com.ciaf.admin.entities.financial.BillsToReceive;
import br.com.ciaf.admin.exception.ResourceNotFoundException;
import br.com.ciaf.admin.repositories.BillsToReceiveRepository;

import javax.transaction.Transactional;

@Service
public class BillsToReceiveServices extends GeralService {
	
	private final BillsToReceiveRepository repository;

	private final CaixaDiarioRepository caixaDiarioRepository;

	public BillsToReceiveServices(BillsToReceiveRepository repository, CaixaDiarioRepository caixaDiarioRepository) {
		this.repository = repository;
		this.caixaDiarioRepository = caixaDiarioRepository;
	}

	public List<BillsToReceive> findAll(){		
		return repository.findAll();
	}

	public List<BillsToReceive> findAllWithEmitenteId(Long emitente_id){
		return findAllByEmitenteId(repository, emitente_id);
	}
	
	public BillsToReceive findById(Long id) {
		Optional<BillsToReceive> credito = repository.findById(id);
		return credito.orElseThrow(() -> new ResourceNotFoundException(id));
	}
	public List<BillsToReceive> findBySaleId(Long id) {
		List<BillsToReceive> credito = repository.findBySaleId(id);
		return credito;
	}
	public List<BillsToReceive> findBySaleNumber(Integer nr_venda) {
		List<BillsToReceive> credito = repository.findBySaleNumber(nr_venda);
		return credito;
	}
	public List<BillsToReceive> findByNfceNumber(int nr_nfce) {
		List<BillsToReceive> credito = repository.findByNfceNumber(nr_nfce);
		return credito;
	}
		
	public List<BillsToReceive> getAllCurrentMonth(){		
		return repository.findByCurrentMonth();
	}

	public List<BillsToReceive> getDueDateMonth(Long emitente_id, String mes){
		return repository.findByDueDateMonth(emitente_id, mes);
	}

	public List<BillsToReceive> findIntervalData(Long emitente_id, LocalDate data1, LocalDate data2){
		return repository.findByDueDateInterval(emitente_id, data1, data2);
	}
	public List<BillsToReceive> getEffectivePayment(Long emitente_id){
		return repository.findByEfetive(emitente_id);
	}
	public List<BillsToReceive> getNoPaid(Long emitente_id){
		return repository.findByNoEfetive(emitente_id);
	}
			
	public BillsToReceive create(BillsToReceive credito) {
		return repository.save(credito);
	}
	@Transactional
	public List<BillsToReceive> saveAll(List<BillsToReceive> recebimentos){
//		List<BillsToReceive> newRecebimentos = repository.saveAll(recebimentos);
		return repository.saveAll(recebimentos);
	}

	public BillsToReceive update(Long id, BillsToReceive credito) {		
		credito.setId(id);
		return repository.save(credito);
	}

	@Transactional
	public BillsToReceive updatePartialPayment(Long id, BillsToReceive credito, Double valorCaixa) {
		credito.setId(id);
		BillsToReceive savedCredito = repository.save(credito);
		CaixaDiario caixaDiario = new CaixaDiario();
		caixaDiario.setDatamov_caixa(new Date());
		caixaDiario.setObservacao("Pagamento parcial através do contas a receber");
		caixaDiario.setMovType(MovType.CREDITO);
		caixaDiario.setOpened(true);
		caixaDiario.setCodigo(15006);
		caixaDiario.setDocumento("PAGAMENTO PARCIAL DENTRO DO CIAF");
		caixaDiario.setValor(valorCaixa);
		caixaDiario.setEmitente_id(savedCredito.getEmitente_id());
		caixaDiario.setNr_venda(savedCredito.getNr_venda());
		caixaDiario.setNr_nfce(savedCredito.getNr_nfce());

		caixaDiarioRepository.save(caixaDiario);

		return savedCredito;
	}

	public void delete(Long id) {
		repository.deleteById(id);
	}

	@Transactional
	public void deleteByVendaId(Long nr_venda) {
		repository.deleteByVendaId(nr_venda);
	}

	@Transactional
	public void deleteByNrVenda(Integer nr_venda) {
		repository.deleteByNrVenda(nr_venda);
	}
	@Transactional
	public void deleteByNrNfce(Integer nr_nfce) {
		repository.deleteByNrNfce(nr_nfce);
	}
}
