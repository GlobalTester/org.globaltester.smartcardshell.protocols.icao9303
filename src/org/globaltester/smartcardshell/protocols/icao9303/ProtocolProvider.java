package org.globaltester.smartcardshell.protocols.icao9303;

import java.util.List;

import org.globaltester.smartcardshell.protocols.AbstractScshProtocolProvider;
import org.globaltester.smartcardshell.protocols.ScshCommand;

public class ProtocolProvider extends AbstractScshProtocolProvider {
	
	@SuppressWarnings("static-access")
	public static String computeMRZCheckDigit(String str) {
		MRZ mrz = new MRZ(str);
		return mrz.computeCheckDigit(str);
	}

	@Override
	public void addCommands(List<ScshCommand> commandList) {
	}

}
