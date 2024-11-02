using UnityEngine;

public class ParallaxBackground : MonoBehaviour
{
    public Transform player; // 플레이어의 Transform
    public float parallaxEffectMultiplier; // 배경의 패럴랙스 효과 배수
    public Vector2 minPosition; // 배경의 최소 위치 (왼쪽 끝)
    public Vector2 maxPosition; // 배경의 최대 위치 (오른쪽 끝)

    private Vector3 lastPlayerPosition; // 마지막 플레이어 위치

    void Start()
    {
        lastPlayerPosition = player.position; // 시작할 때의 플레이어 위치 저장
    }

    void Update()
    {
        // 플레이어의 이동량 계산
        float deltaX = player.position.x - lastPlayerPosition.x;

        // 배경 이동
        transform.position += new Vector3(deltaX * parallaxEffectMultiplier, 0, 0);

        // 배경 위치 제한
        transform.position = new Vector3(
            Mathf.Clamp(transform.position.x, minPosition.x, maxPosition.x),
            transform.position.y,
            transform.position.z
        );

        lastPlayerPosition = player.position; // 마지막 플레이어 위치 업데이트
    }
}
