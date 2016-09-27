package org.tedu.cloudnote.service;

import org.tedu.cloudnote.util.NoteResult;

public interface ShareService {
	public NoteResult favorShare(
		String shareId,String userId);
	public NoteResult loadShare(String shareId);
	public NoteResult searchShare(String keyword);
	public NoteResult shareNote(String noteId);
}
