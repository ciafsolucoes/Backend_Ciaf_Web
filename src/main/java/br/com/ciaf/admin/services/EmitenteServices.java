package br.com.ciaf.admin.services;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import br.com.ciaf.admin.entities.CondicaoPg;
import br.com.ciaf.admin.repositories.CondicaoPgRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import br.com.ciaf.admin.entities.Emitente;
import br.com.ciaf.admin.exception.ResourceNotFoundException;
import br.com.ciaf.admin.repositories.EmitenteRepository;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmitenteServices {
	

	private final EmitenteRepository repository;
	private final CondicaoPgRepository cPgrepository;

    public EmitenteServices(EmitenteRepository repository, CondicaoPgRepository cPgrepository) {
        this.repository = repository;
        this.cPgrepository = cPgrepository;
    }
	// @Scheduled(cron = "0 * * * * ?") // run EVERY MINUTE to test
	@Scheduled(cron = "0 0 0 * * ?") // Runs daily at midnight
	public void checkPaymentAlerts() {
		LocalDate today = LocalDate.now();
		List<Emitente> emitentes = repository.findAll();
        if(!emitentes.isEmpty()) {
			for (Emitente emitente : emitentes) {
				LocalDate paymentDate = emitente.getPaymentDate();
				if (paymentDate != null && paymentDate.isBefore(today.minusYears(1))) {
					generateAlert(emitente);
				}
			}
		}

	}
	private void generateAlert(Emitente emitente) {
		// Implement your alert logic (e.g., send an email or log the alert)
		System.out.println("ALERT: Payment overdue for Emitente: " + emitente.getEmit_XNome());
		repository.updateAsNoPaidById(emitente.getId());
	}



	public List<Emitente> findAll(){
		return repository.findAll();
	}
	
	public Emitente findById(Long id) {
		Optional<Emitente> emitente = repository.findById(id);
		return emitente.orElseThrow(() -> new ResourceNotFoundException(id));
	}

	public String getCertPassword(Long emitente_id){
		return repository.getPassword(emitente_id);
	}

	public Boolean findIfActiveId(Long id) {
        return repository.findIfActiveEmitente(id);
	}
	public Boolean findIfPaidId(Long id) {
		return repository.findIfPaidEmitente(id);
	}
	@Transactional
	public boolean activateEmitente(Long id) {
		int rowsUpdated = repository.updateIsPaidById(id, LocalDate.now());
		return rowsUpdated > 0;
	}

	public Long findLastId() {
		return repository.findLastEmitenteId();
	}

	public Emitente findLastComplete(){
		Optional<Emitente> emitente  = repository.findLastEmitenteComplete();
		return emitente.orElseThrow();
	}

	public LocalDate getStartDate(Long emitente_id){
		LocalDate startDate = repository.getStartDate(emitente_id);
		return startDate;
	}
	
	public Emitente create(Emitente emitente) throws IOException {
//		CondicaoPg condicaoPg = new CondicaoPg();
//		condicaoPg.setDescription();
		Process p = Runtime.getRuntime().exec(new String[]{"bash", "-c", "./myBash.sh"});
		return repository.save(emitente);
	}
	
	public Emitente update(Long id, Emitente emitente) {		
		emitente.setId(id);
		return repository.save(emitente);
	}
	
	public void delete(Long id) {
		repository.deleteById(id);
	}
}
