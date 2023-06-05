import json
import secrets

from django.http import JsonResponse
from django.views.decorators.csrf import csrf_exempt

from kebabmania.models import Usuario


@csrf_exempt
def crearsesion(request):
    if request.method != 'POST':
        return JsonResponse({'error': 'Unsupported HTTP method'}, status=405)

    body_json = json.loads(request.body)
    json_telefono = body_json.get('telefono', None)

    if json_telefono is None:
        return JsonResponse({"error": "Missing parameter"}, status=400)

    try:
        user = Usuario.objects.get(telefono=json_telefono)
    except Usuario.DoesNotExist:
        return JsonResponse({"error": "User does not exist"}, status=404)

    random_token = secrets.token_hex(10)  # Genera un token al usuario y se lo asigna
    user.token = random_token
    user.save()

    return JsonResponse({"user_session_token": random_token, "user_tlf": user.telefono, "user_name": user.nombre}, status=201)

def keep_login(request):
    #Si el metodo no es un get saltara un error
    if request.method != 'GET':
        return JsonResponse({'error': 'Unsupported HTTP method'}, status=405)
    #Recupera el token de la peticion
    token = request.META.get('HTTP_SESSION_TOKEN')
    #Si el progenitor no existe con ese token devolvera error
    if token is None:
        return JsonResponse({'error': 'Missing token header'}, status = 403)

    user_row = Usuario.objects.get(token=token)

    #si existe devolvera el json del usuario
    json_response = user_row.to_json()
    return JsonResponse(json_response, safe=False, status=200)
