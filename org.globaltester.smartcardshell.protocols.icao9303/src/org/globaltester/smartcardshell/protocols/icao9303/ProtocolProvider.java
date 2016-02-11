package org.globaltester.smartcardshell.protocols.icao9303;

import java.util.List;

import org.globaltester.smartcardshell.protocols.AbstractScshProtocolProvider;
import org.globaltester.smartcardshell.protocols.ScshCommand;

public class ProtocolProvider extends AbstractScshProtocolProvider {
	
	public static String computeMRZCheckDigit(String str) {
		return MRZ.computeCheckDigit(str);
	}

	@Override
	public void addCommands(List<ScshCommand> commandList) {
	}

}
