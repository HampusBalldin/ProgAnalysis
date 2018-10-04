package eda045f.exercises;

import java.util.Map;

import eda045f.exercises.flowgraph.UnionSetDomainSet;
import eda045f.exercises.flowgraph.implementation.LiveFlow;
import soot.Body;
import soot.BodyTransformer;
import soot.Local;
import soot.Unit;
import soot.Value;
import soot.jimple.AssignStmt;
import soot.toolkits.graph.CompleteUnitGraph;

public class LiveVarAnalysis extends BodyTransformer {

	@Override
	protected void internalTransform(Body b, String phaseName, Map<String, String> options) {
		UnionSetDomainSet<Value> ld = new UnionSetDomainSet<Value>();
		LiveFlow fg = new LiveFlow(new CompleteUnitGraph(b), ld);
		
		for (Unit u : b.getUnits()) {
			if (!(u instanceof AssignStmt))
			    continue;

            AssignStmt a = (AssignStmt) u;

            if (!(a.getLeftOp() instanceof Local))
                continue;

            Local l = (Local) a.getLeftOp();

            if (!fg.getFlowBefore(u).contains(l)) {
                System.out.println("UVD " + b.getMethod().getSignature() + " " + u.getJavaSourceStartLineNumber() + " (" + u + ")");
            }
		}
	}
}
