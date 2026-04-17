package domain.service.visitor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import domain.service.ServiceComposite;
import domain.service.ServiceLeaf;

/**
 * Visitor that collects textual descriptions of each service visited.
 */
public class DescriptionVisitor implements ServiceVisitor {

	private final List<String> descriptions = new ArrayList<>();

	@Override
	public void visitServiceLeaf(ServiceLeaf leaf) {
		descriptions.add(leaf.getName() + ": " + leaf.getDescription());
	}

	@Override
	public void visitServiceComposite(ServiceComposite composite) {
		descriptions.add("Composite " + composite.getName());
	}

	public List<String> getDescriptions() {
		return Collections.unmodifiableList(descriptions);
	}
}
