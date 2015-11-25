package models.timeline;

import java.util.List;

import models.Dica;

/**
 * Created by dekozo on 11/24/15.
 */
public class TimelineMaisDiscordancias implements TimelineStrategy {

	@Override
	public void sortDicas(List<Dica> dicas) {
		dicas.sort((d1, d2) -> Long.compare(d2.getDiscordancias(), d1.getDiscordancias()));
	}
}
