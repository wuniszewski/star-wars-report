package pl.wuniszewski.starwarsreport.report.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.wuniszewski.starwarsreport.report.entity.Report;

public interface ReportRepository extends JpaRepository<Report, Long> {
}
