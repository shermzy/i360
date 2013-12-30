package CP_Classes.vo;

public class voKeyBehaviourQuestion {

	private int	iPKKeyBehaviourQuestion	= 0;
	private int	iFKRScale	= 0;
	private int	iFKKeyBehaviour = 0;
	private int	iKeyBehaviourQuestionDesc	= 0;
	private int	iKeyBehaviourQuestionEnum	= 0;
	
	public int getFKKeyBehaviour() {
		return iFKKeyBehaviour;
	}
	public void setFKKeyBehaviour(int keyBehaviour) {
		iFKKeyBehaviour = keyBehaviour;
	}
	public int getFKRScale() {
		return iFKRScale;
	}
	public void setFKRScale(int scale) {
		iFKRScale = scale;
	}
	public int getKeyBehaviourQuestionDesc() {
		return iKeyBehaviourQuestionDesc;
	}
	public void setKeyBehaviourQuestionDesc(int keyBehaviourQuestionDesc) {
		iKeyBehaviourQuestionDesc = keyBehaviourQuestionDesc;
	}
	public int getKeyBehaviourQuestionEnum() {
		return iKeyBehaviourQuestionEnum;
	}
	public void setKeyBehaviourQuestionEnum(int keyBehaviourQuestionEnum) {
		iKeyBehaviourQuestionEnum = keyBehaviourQuestionEnum;
	}
	public int getPKKeyBehaviourQuestion() {
		return iPKKeyBehaviourQuestion;
	}
	public void setPKKeyBehaviourQuestion(int keyBehaviourQuestion) {
		iPKKeyBehaviourQuestion = keyBehaviourQuestion;
	}

	
}
